package tom.patrzalek.javadevex3.service;

import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tom.patrzalek.javadevex3.model.User;
import tom.patrzalek.javadevex3.model.CustomerDetails;
import tom.patrzalek.javadevex3.repository.UserRepository;
import tom.patrzalek.javadevex3.service.stripe.CustomerService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CustomerService customerService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByEmail(username);
        try {
            if (!user.isPresent() || !customerService.doesCustomerExits(username)) {
                throw new UsernameNotFoundException("User with email: " + username + " not found.");
            }
        } catch (UsernameNotFoundException | StripeException e) {
            e.printStackTrace();
        }

        return user.map(CustomerDetails::new).get();
    }

}
