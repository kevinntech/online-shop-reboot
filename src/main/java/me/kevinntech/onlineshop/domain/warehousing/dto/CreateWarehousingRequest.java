package me.kevinntech.onlineshop.domain.warehousing.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
public class CreateWarehousingRequest {

    @Positive
    private Long productId;

    @Min(0)
    private long warehousingPrice;

    @Min(0)
    private long warehousingQuantity;

    @Builder
    public CreateWarehousingRequest(Long productId, long warehousingPrice, long warehousingQuantity) {
        this.productId = productId;
        this.warehousingPrice = warehousingPrice;
        this.warehousingQuantity = warehousingQuantity;
    }

    public CreateWarehousingDto toDto() {
        return CreateWarehousingDto.builder()
                .productId(productId)
                .price(warehousingPrice)
                .quantity(warehousingQuantity)
                .build();
    }

}
