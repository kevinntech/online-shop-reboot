package me.kevinntech.onlineshop.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateProductForm {

    private String code;

    private String name;

    private String brand;

    private long price;

    private String description;

    private String productImage;

    public CreateProductForm(String code, String name, String brand, long price, String description, String productImage) {
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.description = description;
        this.productImage = productImage;
    }

}
