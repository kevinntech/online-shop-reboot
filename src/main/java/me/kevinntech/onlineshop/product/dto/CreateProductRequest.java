package me.kevinntech.onlineshop.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kevinntech.onlineshop.product.Product;
import me.kevinntech.onlineshop.product.annotation.UniqueProductCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class CreateProductRequest {

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
    public CreateProductRequest(Product product) {
        this.code = product.getCode();
        this.name = product.getName();
        this.brand = product.getBrand();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.productImage = product.getProductImage();
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
