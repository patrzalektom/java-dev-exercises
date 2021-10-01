package tom.patrzalek.javadevex3.service.mapper;

import com.stripe.param.CustomerCreateParams;
import org.springframework.stereotype.Component;
import tom.patrzalek.javadevex3.model.User;

@Component
public class CustomerMapper {

    public CustomerCreateParams toStripeEntityParams(User user) {
        return CustomerCreateParams.builder()
                .setName(user.getName())
                .setEmail(user.getEmail())
                .setDescription(user.getDescription())
                .build();
    }

}
