package me.kevinntech.onlineshop.product;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(of = "id")
@SequenceGenerator(
        name = "PRODUCT_SEQ_GENERATOR",
        sequenceName = "PRODUCT_SEQ"
)
@Table(
        name = "PRODUCT",
        uniqueConstraints = {
                @UniqueConstraint(name = "UQ__PRODUCT__PRODUCT_ID", columnNames = "PRODUCT_CODE")
        }
)
@Entity
public class Product {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "PRODUCT_SEQ_GENERATOR"
    )
    @Column(name = "PRODUCT_ID")
    private Long id;

    @Column(name = "PRODUCT_CODE")
    private String code;

    @Column(name = "PRODUCT_NAME")
    private String name;

    private String brand;

    @Column(name = "PRODUCT_PRICE")
    private long price;

    private String description;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String productImage;

}
