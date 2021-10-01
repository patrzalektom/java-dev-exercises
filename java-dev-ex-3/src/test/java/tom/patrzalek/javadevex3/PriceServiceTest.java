package tom.patrzalek.javadevex3;

import com.stripe.Stripe;
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
import tom.patrzalek.javadevex3.service.stripe.PriceService;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class PriceServiceTest {

    private CreateInvoiceDTO createInvoiceDTO;
    private List<CreateInvoiceDTO> createDTOs;
    private Price price;

    @Value("${stripe.secret}")
    private String apiKey;

    @Mock
    private PriceService priceService;

    @BeforeEach
    public void setup() {
        createInvoiceDTO = new CreateInvoiceDTO();
        createInvoiceDTO.setProductName("testProduct");
        createInvoiceDTO.setPriceCurrency("pln");
        createInvoiceDTO.setPriceUnitAmount(1000L);
        price = new Price();
        price.setCurrency("pln");
        price.setUnitAmount(1000L);
        createDTOs = Collections.singletonList(createInvoiceDTO);
        Stripe.apiKey = apiKey;
    }

    @Test
    public void shouldCreatePrice() {
        Mockito.when(priceService.create(createDTOs)).thenReturn(Collections.singletonList(price));
        List<Price> createdPrices = priceService.create(createDTOs);
        assertFalse(createdPrices.isEmpty());
        assertThat(createdPrices.stream().findFirst().get().getCurrency()).isEqualTo(price.getCurrency());
        assertThat(createdPrices.stream().findFirst().get().getUnitAmount()).isEqualTo(price.getUnitAmount());
    }

}
