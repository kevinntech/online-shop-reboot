package me.kevinntech.onlineshop.product.annotation;

import me.kevinntech.onlineshop.product.validator.UniqueProductCodeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueProductCodeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueProductCode {
    String message() default "이미 존재하는 상품코드입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
