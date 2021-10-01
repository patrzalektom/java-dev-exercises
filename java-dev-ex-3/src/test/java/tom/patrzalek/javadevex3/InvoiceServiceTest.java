package tom.patrzalek.javadevex3;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Invoice;
import com.stripe.model.InvoiceItem;
import com.stripe.model.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import tom.patrzalek.javadevex3.model.CreateInvoiceDTO;
import tom.patrzalek.javadevex3.service.stripe.InvoiceService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class InvoiceServiceTest {

    private CreateInvoiceDTO createInvoiceDTO;
    private List<CreateInvoiceDTO> createDTOs;
    private Price price;
    private Invoice invoiceOne;
    private Invoice invoiceTwo;
    private InvoiceItem invoiceItem;
    private List<Invoice> invoices;

    @Value("${stripe.secret}")
    private String apiKey;

    @Mock
    private InvoiceService invoiceService;

    @BeforeEach
    public void setup() {
        createInvoiceDTO = new CreateInvoiceDTO();
        createInvoiceDTO.setProductName("testProduct");
        createInvoiceDTO.setPriceCurrency("pln");
        createInvoiceDTO.setPriceUnitAmount(1000L);
        price = new Price();
        price.setCurrency("pln");
        price.setUnitAmount(1000L);
        InvoiceItem invoiceItem = new InvoiceItem();
        invoiceItem.setCustomer("testCustomerId");
        invoiceItem.setPrice(price);
        invoiceOne = new Invoice();
        invoiceOne.setId("testInvoiceId");
        invoiceOne.setCustomer("testCustomerId");
        invoiceTwo = new Invoice();
        invoiceTwo.setId("testInvoiceIdTwo");
        invoiceTwo.setCustomer("testCustomerId");
        invoices = Arrays.asList(invoiceOne, invoiceTwo);
        createDTOs = Collections.singletonList(createInvoiceDTO);
        Stripe.apiKey = apiKey;
    }

    @Test
    public void shouldCreateInvoice() throws StripeException {
        Mockito.when(invoiceService.create(createDTOs)).thenReturn(invoiceOne);
        Invoice createdInvoice = invoiceService.create(createDTOs);
        assertThat(invoiceOne.getId()).isEqualTo(createdInvoice.getId());
    }

    @Test
    public void shouldGetAllInvoicesForCurrentCustomer() throws StripeException {
        Mockito.when(invoiceService.getAllInvoicesForCurrentCustomer()).thenReturn(invoices);
        List<Invoice> foundInvoices = invoiceService.getAllInvoicesForCurrentCustomer();
        assertFalse(foundInvoices.isEmpty());
    }

    @Test
    public void shouldGetInvoiceById() throws StripeException {
        Mockito.when(invoiceService.getInvoiceById(invoiceOne.getId())).thenReturn(invoiceOne);
        Invoice foundInvoice = invoiceService.getInvoiceById(invoiceOne.getId());
        assertThat(foundInvoice.getId()).isEqualTo(invoiceOne.getId());
    }

}
