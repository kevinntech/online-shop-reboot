package me.kevinntech.onlineshop.product.service;

import me.kevinntech.onlineshop.product.Product;
import me.kevinntech.onlineshop.product.dto.ProductDto;
import me.kevinntech.onlineshop.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("비즈니스 로직 - 상품")
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks ProductService productService;
    @Mock ProductRepository productRepository;

    @DisplayName("[Service] 상품 등록 - 성공")
    @Test
    void createProduct_success() {
        // Given
        Product product = buildProduct("CODE-001", "운동화", "나이키", 30000L);
        ProductDto productDto = ProductDto.fromEntity(product);
        given(productRepository.save(any(Product.class))).willReturn(product);

        // When
        Long productId = productService.createProduct(productDto);

        // Then
        assertThat(productId).isEqualTo(1L);
        then(productRepository).should().save(any(Product.class));
    }

    @DisplayName("[Service] 상품 등록 - 실패 (상품 정보를 전달하지 않는 경우)")
    @Test
    void createProduct_fail() {
        // Given

        // When
        Long productId = productService.createProduct(null);

        // Then
        assertThat(productId).isNull();
        then(productRepository).shouldHaveNoInteractions();
    }

    @DisplayName("[Service] 상품 목록 조회 - 성공")
    @Test
    void getProductsOrderById_success() {
        // Given
        given(productRepository.findProductsOrderById()).willReturn(
                List.of(
                        buildProduct("CODE-001", "나이키 운동화", "나이키", 30000L),
                        buildProduct("CODE-002", "아디다스 운동화", "아디다스", 31000L),
                        buildProduct("CODE-003", "뉴발란스 운동화", "뉴발란스", 32000L)
                )
        );

        // When
        List<ProductDto> productDtoList = productService.getProductsOrderById();

        // Then
        assertThat(productDtoList.size()).isEqualTo(3);
        ProductDto productDto = productDtoList.get(0);
        assertThat(productDto.getCode()).isEqualTo("CODE-001");
        assertThat(productDto.getName()).isEqualTo("나이키 운동화");
        assertThat(productDto.getBrand()).isEqualTo("나이키");
        assertThat(productDto.getPrice()).isEqualTo(30000L);
        then(productRepository).should().findProductsOrderById();
    }

    @DisplayName("[Service] 상품 목록 조회 - 비어 있음 (등록된 상품 정보가 없는 경우)")
    @Test
    void getProductsOrderById_empty() {
        // Given
        given(productRepository.findProductsOrderById()).willReturn(new ArrayList<>());

        // When
        List<ProductDto> productDtoList = productService.getProductsOrderById();

        // Then
        assertThat(productDtoList.size()).isEqualTo(0);
        then(productRepository).should().findProductsOrderById();
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

        ReflectionTestUtils.setField(product, "id", 1L);

        return product;
    }

}