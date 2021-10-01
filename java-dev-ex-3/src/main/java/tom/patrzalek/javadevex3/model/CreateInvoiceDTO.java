package tom.patrzalek.javadevex3.model;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class CreateInvoiceDTO {

    @NotNull
    private String productName;

    @NotNull
    private Long priceUnitAmount;

    @NotNull
    private String priceCurrency;

}
