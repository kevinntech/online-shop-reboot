package me.kevinntech.onlineshop.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kevinntech.onlineshop.product.annotation.UniqueProductCode;

@Getter
@NoArgsConstructor
public class ValidateProductCodeRequest {

    @UniqueProductCode
    private String code;

}
