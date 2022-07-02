package me.kevinntech.onlineshop.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kevinntech.onlineshop.product.Product;

@Getter
@NoArgsConstructor
public class ProductDto {

    private Long id;

    private String code;

    private String name;

    private String brand;

    private long price;

    private String description;

    private String productImage;

    @Builder
    public ProductDto(Long id, String code, String name, String brand, long price, String description, String productImage) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.description = description;
        this.productImage = productImage;
    }

    public Product toEntity() {
        return Product.builder()
                .code(code)
                .name(name)
                .brand(brand)
                .price(price)
                .description(description)
                .productImage(productImage)
                .build();
    }

}
