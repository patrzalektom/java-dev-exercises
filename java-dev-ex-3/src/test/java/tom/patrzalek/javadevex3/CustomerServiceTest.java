package tom.patrzalek.javadevex3;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import tom.patrzalek.javadevex3.model.User;
import tom.patrzalek.javadevex3.service.stripe.CustomerService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Value("${stripe.secret}")
    private String apiKey;

    @Mock
    private CustomerService customerService;

    private Customer stripeCustomer;
    private User user;

    @BeforeEach
    public void setup() {
        Stripe.apiKey = apiKey;
        user = new User();
        user.setId("testCustomerId");
        user.setName("testCustomer");
        user.setPassword("testPassword");
        user.setRoles("ROLE_CUSTOMER");
        user.setDescription("testDescription");
        user.setEmail("test@customer.com");

        stripeCustomer = new Customer();
        stripeCustomer.setId("testCustomerId");
        stripeCustomer.setName("testCustomer");
        stripeCustomer.setDescription("testDescription");
        stripeCustomer.setEmail("test@customer.com");
    }

    @Test
    public void shouldReturnCreatedCustomerMessage() throws StripeException {
        Mockito.when(customerService.create(user)).thenReturn(stripeCustomer);
        Customer createdCustomer = customerService.create(user);
        assertThat(createdCustomer.getId()).isEqualTo(stripeCustomer.getId());
    }

    @Test
    public void shouldReturnCurrentCustomer() throws StripeException {
        Mockito.when(customerService.getCurrentCustomer()).thenReturn(stripeCustomer);
        Customer foundCustomer = customerService.getCurrentCustomer();
        assertThat(foundCustomer.getId()).isEqualTo(stripeCustomer.getId());
    }

    @Test
    public void shouldReturnTrueIfCustomerExists() throws StripeException {
        Mockito.when(customerService.doesCustomerExits(user.getEmail())).thenReturn(true);
        boolean exists = customerService.doesCustomerExits(user.getEmail());
        assertTrue(exists);
    }

    @Test
    public void shouldReturnFalseIfCustomerDoesNotExist() throws StripeException {
        Mockito.when(customerService.doesCustomerExits(user.getEmail())).thenReturn(false);
        boolean exists = customerService.doesCustomerExits(user.getEmail());
        assertFalse(exists);
    }

}
