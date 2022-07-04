package me.kevinntech.onlineshop.domain.product.validator;

import lombok.RequiredArgsConstructor;
import me.kevinntech.onlineshop.domain.product.annotation.UniqueProductCode;
import me.kevinntech.onlineshop.domain.product.repository.ProductRepository;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.MessageFormat;

@RequiredArgsConstructor
@Component
public class UniqueProductCodeValidator implements ConstraintValidator<UniqueProductCode, String> {

    private final ProductRepository productRepository;

    @Override
    public void initialize(UniqueProductCode constraintAnnotation) {

    }

    @Override
    public boolean isValid(String productCode, ConstraintValidatorContext context) {
        boolean existsByProductCode = productRepository.existsByCode(productCode);

        if (existsByProductCode) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(MessageFormat.format("{0} 이라는 상품코드는 이미 존재합니다!", productCode))
                    .addConstraintViolation();
        }

        return !existsByProductCode;
    }
}
