package me.kevinntech.onlineshop.domain.warehousing.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kevinntech.onlineshop.domain.product.dto.ProductDto;
import me.kevinntech.onlineshop.domain.stock.StockDto;

@Getter
@NoArgsConstructor
public class WarehousingDto {

    private Long id;

    private ProductDto product;

    private StockDto stock;

    private long price;

    private long quantity;

    @Builder
    public WarehousingDto(Long id, ProductDto product, StockDto stock, long price, long quantity) {
        this.id = id;
        this.product = product;
        this.stock = stock;
        this.price = price;
        this.quantity = quantity;
    }

}