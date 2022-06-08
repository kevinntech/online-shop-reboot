package me.kevinntech.onlineshop.auth;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueNicknameValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueNickname {
    String message() default "이미 존재하는 닉네임입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
