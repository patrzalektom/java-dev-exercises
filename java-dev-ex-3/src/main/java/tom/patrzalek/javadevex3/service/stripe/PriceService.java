package tom.patrzalek.javadevex3.service.stripe;

import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PriceCreateParams;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tom.patrzalek.javadevex3.model.CreateInvoiceDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PriceService {

    private final ProductService productService;

    public List<Price> create(List<CreateInvoiceDTO> createInvoiceDTO) {

        List<Product> products = productService.create(createInvoiceDTO);
        List<PriceCreateParams> createParams = new ArrayList<>();
        for (int i = 0; i < createInvoiceDTO.size(); i++) {
            createParams.add(PriceCreateParams.builder()
                    .setProduct(products.get(i).getId())
                    .setUnitAmount(createInvoiceDTO.get(i).getPriceUnitAmount())
                    .setCurrency(createInvoiceDTO.get(i).getPriceCurrency())
                    .build());
        }

        return createParams.stream().map(param -> {
            try {
                return Price.create(param);
            } catch (StripeException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }

}
