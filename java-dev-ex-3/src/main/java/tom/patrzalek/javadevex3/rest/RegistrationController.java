package tom.patrzalek.javadevex3.rest;

import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tom.patrzalek.javadevex3.model.EmailValidationException;
import tom.patrzalek.javadevex3.model.User;
import tom.patrzalek.javadevex3.service.RegistrationService;

@AllArgsConstructor
@RestController
@RequestMapping("/register")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody User user)
            throws StripeException, EmailValidationException {
        return registrationService.register(user);
    }

}
