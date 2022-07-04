package me.kevinntech.onlineshop.domain.product.repository;

import me.kevinntech.onlineshop.domain.product.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("데이터베이스 - 상품")
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @DisplayName("[데이터베이스] 상품 코드 존재 여부 체크 - 성공")
    @Test
    void existsByCode_success() {
        // Given
        productRepository.save(buildProduct("CODE-001", "나이키 운동화", "나이키", 30000L));

        // When
        boolean existsByCode = productRepository.existsByCode("CODE-002");

        // Then
        assertThat(existsByCode).isEqualTo(false);
    }

    @DisplayName("[데이터베이스] 상품 코드 존재 여부 체크 - 이미 존재하는 데이터")
    @Test
    void existsByCode_exists() {
        // Given
        productRepository.save(buildProduct("CODE-001", "나이키 운동화", "나이키", 30000L));

        // When
        boolean existsByCode = productRepository.existsByCode("CODE-001");

        // Then
        assertThat(existsByCode).isEqualTo(true);
    }

    @DisplayName("[데이터베이스] 상품 목록 조회 - 성공")
    @Test
    void findProductsOrderById_success() {
        // Given
        Product savedProduct1 = productRepository.save(buildProduct("CODE-001", "나이키 운동화", "나이키", 30000L));
        Product savedProduct2 = productRepository.save(buildProduct("CODE-002", "아디다스 운동화", "아디다스", 31000L));

        // When
        List<Product> findProducts = productRepository.findProductsOrderById();

        // Then
        assertThat(findProducts.size()).isEqualTo(2);
        assertThat(findProducts.get(0))
                .hasFieldOrPropertyWithValue("code", savedProduct1.getCode())
                .hasFieldOrPropertyWithValue("name", savedProduct1.getName())
                .hasFieldOrPropertyWithValue("brand", savedProduct1.getBrand())
                .hasFieldOrPropertyWithValue("price", savedProduct1.getPrice());
        assertThat(findProducts.get(1))
                .hasFieldOrPropertyWithValue("code", savedProduct2.getCode())
                .hasFieldOrPropertyWithValue("name", savedProduct2.getName())
                .hasFieldOrPropertyWithValue("brand", savedProduct2.getBrand())
                .hasFieldOrPropertyWithValue("price", savedProduct2.getPrice());
    }

    @DisplayName("[데이터베이스] 상품 목록 조회 - 비어 있음")
    @Test
    void findProductsOrderById_empty() {
        // Given

        // When
        List<Product> findProducts = productRepository.findProductsOrderById();

        // Then
        assertThat(findProducts.size()).isEqualTo(0);
        assertThat(findProducts).isEmpty();
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

}