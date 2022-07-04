package me.kevinntech.onlineshop.domain.warehousing.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateWarehousingDto {

    private Long productId;

    private Long stockId;

    private long price;

    private long quantity;

    @Builder
    public CreateWarehousingDto(Long productId, Long stockId, long price, long quantity) {
        this.productId = productId;
        this.stockId = stockId;
        this.price = price;
        this.quantity = quantity;
    }


}
