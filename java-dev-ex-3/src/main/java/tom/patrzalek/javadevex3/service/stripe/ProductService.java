package tom.patrzalek.javadevex3.service.stripe;

import com.stripe.exception.StripeException;
import com.stripe.model.Product;
import com.stripe.param.ProductCreateParams;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tom.patrzalek.javadevex3.model.CreateInvoiceDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    public List<Product> create(List<CreateInvoiceDTO> createInvoiceDTO) {
        List<ProductCreateParams> params = createInvoiceDTO.stream()
                .map(dto -> ProductCreateParams.builder()
                        .setName(dto.getProductName())
                        .build()).collect(Collectors.toList());

        return params.stream().map(param -> {
            try {
                return Product.create(param);
            } catch (StripeException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }

}
