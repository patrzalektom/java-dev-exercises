package tom.patrzalek.javadevex3.service.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Invoice;
import com.stripe.model.InvoiceItem;
import com.stripe.model.Price;
import com.stripe.param.InvoiceCreateParams;
import com.stripe.param.InvoiceItemCreateParams;
import com.stripe.param.InvoiceListParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tom.patrzalek.javadevex3.model.CreateInvoiceDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    @Value("${stripe.secret}")
    private String apiKey;

    private final PriceService priceService;
    private final CustomerService customerService;

    public Invoice create(List<CreateInvoiceDTO> createInvoiceDTO) throws StripeException {
        Stripe.apiKey = apiKey;
        Customer currentCustomer = customerService.getCurrentCustomer();
        List<Price> prices = priceService.create(createInvoiceDTO);
        List<InvoiceItemCreateParams> createParams = prices.stream()
                .map(price -> InvoiceItemCreateParams.builder()
                        .setCustomer(currentCustomer.getId())
                        .setPrice(price.getId())
                        .build()).collect(Collectors.toList());

        for (InvoiceItemCreateParams createParam : createParams) {
            InvoiceItem.create(createParam);
        }

        InvoiceCreateParams invoiceCreateParams = InvoiceCreateParams.builder()
                .setCustomer(currentCustomer.getId())
                .setAutoAdvance(true)
                .build();

        return Invoice.create(invoiceCreateParams);
    }

    public List<Invoice> getAllInvoicesForCurrentCustomer() throws StripeException {
        Stripe.apiKey = apiKey;
        Customer currentCustomer = customerService.getCurrentCustomer();
        InvoiceListParams listParams = InvoiceListParams.builder()
                .setCustomer(currentCustomer.getId())
                .build();

        return Invoice.list(listParams).getData();
    }

    public Invoice getInvoiceById(String invoiceId) throws StripeException {
        Stripe.apiKey = apiKey;
        return Invoice.retrieve(invoiceId);
    }

}
