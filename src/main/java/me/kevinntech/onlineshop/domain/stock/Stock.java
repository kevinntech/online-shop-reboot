package me.kevinntech.onlineshop.domain.stock;

import lombok.*;
import me.kevinntech.onlineshop.domain.product.Product;
import me.kevinntech.onlineshop.domain.warehousing.Warehousing;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@EqualsAndHashCode(of = "id")
@SequenceGenerator(
        name = "STOCK_SEQ_GENERATOR",
        sequenceName = "STOCK_SEQ"
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Stock {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "STOCK_SEQ_GENERATOR"
    )
    @Column(name = "stock_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "stock")
    @Column(name = "warehousing_id")
    private List<Warehousing> warehousingList = new ArrayList<>();

    @Column(name = "stock_price")
    private long price;

    @Column(name = "stock_quantity")
    private long quantity;

    @Builder
    public Stock(long price, long quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    public void adjustStock() {
        // 재고 조정
        this.quantity = 0;

        // 입고(+)
        for (Warehousing warehousing : warehousingList) {
            this.quantity += warehousing.getQuantity();
        }

    }

}
