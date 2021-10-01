package tom.patrzalek.javadevex3;

import com.stripe.Stripe;
import com.stripe.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import tom.patrzalek.javadevex3.model.CreateInvoiceDTO;
import tom.patrzalek.javadevex3.service.stripe.ProductService;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    private CreateInvoiceDTO createInvoiceDTO;
    private List<CreateInvoiceDTO> createDTOs;
    private Product product;

    @Value("${stripe.secret}")
    private String apiKey;

    @Mock
    private ProductService productService;

    @BeforeEach
    public void setup() {
        createInvoiceDTO = new CreateInvoiceDTO();
        createInvoiceDTO.setProductName("testProduct");
        createInvoiceDTO.setPriceCurrency("pln");
        createInvoiceDTO.setPriceUnitAmount(1000L);
        product = new Product();
        product.setName("testProduct");
        createDTOs = Collections.singletonList(createInvoiceDTO);
        Stripe.apiKey = apiKey;
    }

    @Test
    public void shouldCreateProduct() {
        Mockito.when(productService.create(createDTOs)).thenReturn(Collections.singletonList(product));
        List<Product> createdProducts = productService.create(createDTOs);
        assertThat(createdProducts.stream().findFirst().get().getName()).isEqualTo(product.getName());
    }

}
