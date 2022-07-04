package me.kevinntech.onlineshop.domain.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetProductResponse {

    private Long id;

    private String code;

    private String name;

    private String brand;

    private Long price;

    private String description;

    private String productImage;

    @Builder
    public GetProductResponse(Long id, String code, String name, String brand, Long price, String description, String productImage) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.description = description;
        this.productImage = productImage;
    }

    public static GetProductResponse fromDto(ProductDto productDto) {
        if (productDto == null) {
            return null;
        }

        return GetProductResponse.builder()
                .id(productDto.getId())
                .code(productDto.getCode())
                .name(productDto.getName())
                .brand(productDto.getBrand())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .productImage(productDto.getProductImage())
                .build();
    }
}