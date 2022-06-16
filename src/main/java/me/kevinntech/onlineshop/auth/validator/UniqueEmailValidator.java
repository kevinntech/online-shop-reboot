package me.kevinntech.onlineshop.auth.validator;

import lombok.RequiredArgsConstructor;
import me.kevinntech.onlineshop.auth.annotation.UniqueEmail;
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
            context.buildConstraintViolationWithTemplate(MessageFormat.format("{0} 이라는 이메일은 이미 존재합니다!", email))
                    .addConstraintViolation();
        }

        return !existsByEmail;
    }
}
