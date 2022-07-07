package me.kevinntech.onlineshop.domain.stock;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StockDto {

    private Long id;

    private long price;

    private long quantity;

    @Builder
    public StockDto(Long id, long price, long quantity) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }

}
