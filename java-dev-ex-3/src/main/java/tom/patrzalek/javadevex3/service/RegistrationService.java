package tom.patrzalek.javadevex3.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tom.patrzalek.javadevex3.model.EmailValidationException;
import tom.patrzalek.javadevex3.model.User;
import tom.patrzalek.javadevex3.repository.UserRepository;
import tom.patrzalek.javadevex3.service.stripe.CustomerService;

import javax.transaction.Transactional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private static final String EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern emailPattern = Pattern.compile(EMAIL_REGEX);

    private final CustomerService customerService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String register(User user) throws StripeException, EmailValidationException {
        if (!isEmailCorrect(user.getEmail())) {
            throw new EmailValidationException("Provided email is incorrect.");
        }
        if (isEmailTaken(user.getEmail())) {
            throw new EmailValidationException("Customer with provided email already exists.");
        }
        Customer createdCustomer = customerService.create(user);
        user.setId(createdCustomer.getId());
        saveUser(user);
        return "Customer " + createdCustomer.getEmail() + " registered successfully!";
    }

    @Transactional
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_CUSTOMER");
        userRepository.save(user);
    }

    private boolean isEmailTaken(String email) throws StripeException {
        return userRepository.findByEmail(email).isPresent() || customerService.doesCustomerExits(email);
    }

    private boolean isEmailCorrect(String email) {
        Matcher emailMatcher = emailPattern.matcher(email);
        return emailMatcher.matches();
    }

}
