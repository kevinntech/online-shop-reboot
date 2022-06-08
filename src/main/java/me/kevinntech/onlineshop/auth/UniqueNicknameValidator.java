package me.kevinntech.onlineshop.auth;

import lombok.RequiredArgsConstructor;
import me.kevinntech.onlineshop.user.UserRepository;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.MessageFormat;

@RequiredArgsConstructor
@Component
public class UniqueNicknameValidator implements ConstraintValidator<UniqueNickname,String> {

    private final UserRepository userRepository;

    @Override
    public void initialize(UniqueNickname constraintAnnotation) {
    }

    @Override
    public boolean isValid(String nickname, ConstraintValidatorContext context) {
        boolean existsByNickname = userRepository.existsByNickname(nickname);

        if (existsByNickname) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(MessageFormat.format("Nickname {0} already exists!", nickname))
                    .addConstraintViolation();
        }

        return !existsByNickname;
    }
}
