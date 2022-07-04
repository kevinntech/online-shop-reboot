package me.kevinntech.onlineshop.domain.warehousing;

import lombok.*;
import me.kevinntech.onlineshop.domain.product.Product;
import me.kevinntech.onlineshop.domain.stock.Stock;

import javax.persistence.*;

@Getter
@EqualsAndHashCode(of = "id")
@SequenceGenerator(
        name = "WAREHOUSING_SEQ_GENERATOR",
        sequenceName = "WAREHOUSING_SEQ"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Warehousing {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "WAREHOUSING_SEQ_GENERATOR"
    )
    @Column(name = "WAREHOUSING_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Column(name = "WAREHOUSING_PRICE")
    private long price;

    @Column(name = "WAREHOUSING_QUANTITY")
    private long quantity;

    @Builder
    public Warehousing(long price, long quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    /*
    * 연관관계 편의 메소드
    * */

    public void changeProduct(Product product) {
        this.product = product;
    }

    public void changeStock(Stock stock) {
        this.stock = stock;
        stock.getWarehousingList().add(this);
        stock.adjustStock(); // 재고 조정
    }

    public void establishRelationship(Product product, Stock stock) {
        changeProduct(product);
        changeStock(stock);
    }

}