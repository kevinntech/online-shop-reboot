package me.kevinntech.onlineshop.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kevinntech.onlineshop.product.annotation.UniqueProductCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UpdateProductRequest {

    @UniqueProductCode
    @NotBlank
    @Length(min = 1, max = 50)
    private String code;

    @NotBlank
    @Length(min = 1, max = 50)
    private String name;

    @NotBlank
    @Length(min = 1, max = 50)
    private String brand;

    @Min(0)
    private long price;

    private String description;

    private String productImage;

    @Builder
    public UpdateProductRequest(String code, String name, String brand, long price, String description, String productImage) {
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.description = description;
        this.productImage = productImage;
    }

    public ProductDto toDto() {
        return ProductDto.builder()
                .code(code)
                .name(name)
                .brand(brand)
                .price(price)
                .description(description)
                .productImage(productImage)
                .build();
    }

}