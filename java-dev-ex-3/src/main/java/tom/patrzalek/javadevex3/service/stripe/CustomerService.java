package tom.patrzalek.javadevex3.service.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerListParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tom.patrzalek.javadevex3.model.User;
import tom.patrzalek.javadevex3.service.mapper.CustomerMapper;

@RequiredArgsConstructor
@Service
public class CustomerService {

    @Value("${stripe.secret}")
    private String apiKey;

    private final CustomerMapper customerMapper;

    public Customer create(User user) throws StripeException {
        Stripe.apiKey = apiKey;
        return Customer.create(customerMapper.toStripeEntityParams(user));
    }

    public Customer getCurrentCustomer() throws StripeException {
        Stripe.apiKey = apiKey;
        String currentCustomerEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        CustomerListParams listParams = CustomerListParams.builder()
                .setEmail(currentCustomerEmail)
                .build();
        return Customer.list(listParams).getData().stream().findFirst().get();
    }

    public boolean doesCustomerExits(String email) throws StripeException {
        Stripe.apiKey = apiKey;
        CustomerListParams listParams = CustomerListParams.builder()
                .setEmail(email)
                .build();
        return !Customer.list(listParams).getData().isEmpty();
    }

}
