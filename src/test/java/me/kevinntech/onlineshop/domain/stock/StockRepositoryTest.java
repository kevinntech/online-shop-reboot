package me.kevinntech.onlineshop.domain.stock;

import me.kevinntech.onlineshop.domain.product.Product;
import me.kevinntech.onlineshop.domain.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("데이터베이스 - 재고")
@DataJpaTest
class StockRepositoryTest {

    @Autowired ProductRepository productRepository;
    @Autowired StockRepository stockRepository;

    @DisplayName("[데이터베이스] 상품 ID에 해당하는 재고 조회 - 성공")
    @Test
    void findStockByProductId_success() {
        // Given
        Product product = buildProduct("CODE-001", "운동화", "나이키", 30000L);
        productRepository.save(product);
        stockRepository.save(buildStock(30000L, 10L, product));

        // When
        Stock findStock = stockRepository.findStockByProductId(product.getId());

        // Then
        assertThat(findStock.getPrice()).isEqualTo(30000L);
        assertThat(findStock.getQuantity()).isEqualTo(10L);
    }

    @DisplayName("[데이터베이스] 상품 ID에 해당하는 재고 조회 - 실패 (존재하지 않는 상품 ID로 조회하므로 재고 정보가 없음)")
    @Test
    void findStockByProductId_fail() {
        // Given
        Long wrongProductId = 0L;

        // When
        Stock findStock = stockRepository.findStockByProductId(wrongProductId);

        // Then
        assertThat(findStock).isNull();
    }

    @DisplayName("[데이터베이스] 상품 ID에 해당하는 재고가 존재하는지 여부 체크 - 성공")
    @Test
    void existsStockByProductId_success() {
        // Given
        Product product = buildProduct("CODE-001", "운동화", "나이키", 30000L);
        productRepository.save(product);
        stockRepository.save(buildStock(30000L, 10L, product));

        // When
        boolean existsStockByProductId = stockRepository.existsStockByProductId(product.getId());

        // Then
        assertThat(existsStockByProductId).isEqualTo(true);
    }

    @DisplayName("[데이터베이스] 상품 ID에 해당하는 재고가 존재하는지 여부 체크 - 실패 (존재하지 않는 상품 ID로 조회 했으므로 재고 정보가 없음)")
    @Test
    void existsStockByProductId_fail() {
        // Given
        Long wrongProductId = 0L;

        // When
        boolean existsStockByProductId = stockRepository.existsStockByProductId(wrongProductId);

        // Then
        assertThat(existsStockByProductId).isEqualTo(false);
    }

    private Product buildProduct(String code, String name, String brand, Long price) {
        Product product = Product.builder()
                .code(code)
                .name(name)
                .brand(brand)
                .price(price)
                .description("")
                .productImage("")
                .build();

        return product;
    }

    private Stock buildStock(Long price, Long quantity, Product product) {
        Stock stock = Stock.builder()
                .price(price)
                .quantity(quantity)
                .build();
        stock.changeProduct(product);

        return stock;
    }
}