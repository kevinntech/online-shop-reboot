package me.kevinntech.onlineshop.domain.product;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kevinntech.onlineshop.domain.product.dto.ProductDto;
import me.kevinntech.onlineshop.domain.stock.Stock;

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

    @OneToOne(mappedBy = "product")
    private Stock stock;

    public void changeStock(Stock stock) {
        this.stock = stock;
        stock.changeProduct(this);
    }

    @Builder
    public Product(String code, String name, String brand, long price, String description, String productImage) {
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.description = description;
        this.productImage = productImage;
    }

    public void update(ProductDto productDto) {
        if (productDto.getCode() != null) { this.code = productDto.getCode(); }
        if (productDto.getName() != null) { this.name = productDto.getName(); }
        if (productDto.getBrand() != null) { this.brand = productDto.getBrand(); }
        if (productDto.getPrice() >= 0) { this.price = productDto.getPrice(); }
        if (productDto.getDescription() != null) { this.description = productDto.getDescription(); }
        if (productDto.getProductImage() != null) { this.productImage = productDto.getProductImage(); }
    }
}
