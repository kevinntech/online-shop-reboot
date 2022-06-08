package me.kevinntech.onlineshop.auth;

import lombok.RequiredArgsConstructor;
import me.kevinntech.onlineshop.user.UserRepository;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.MessageFormat;

@RequiredArgsConstructor
@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserRepository userRepository;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        boolean existsByEmail = userRepository.existsByEmail(email);

        if (existsByEmail) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(MessageFormat.format("Email {0} already exists!", email))
                    .addConstraintViolation();
        }

        return !existsByEmail;
    }
}
