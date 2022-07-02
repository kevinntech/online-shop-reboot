package me.kevinntech.onlineshop.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kevinntech.onlineshop.product.annotation.UniqueProductCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class ValidateProductCodeRequest {

    @UniqueProductCode
    @NotBlank
    @Length(min = 1, max = 50)
    private String code;

    @Builder
    public ValidateProductCodeRequest(String code) {
        this.code = code;
    }

}
