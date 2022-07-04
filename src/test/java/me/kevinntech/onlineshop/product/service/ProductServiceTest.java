package me.kevinntech.onlineshop.product.service;

import me.kevinntech.onlineshop.base.BusinessException;
import me.kevinntech.onlineshop.base.ErrorCode;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

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

    @DisplayName("[Service] 상품 수정 - 성공")
    @Test
    void updateProduct_success() {
        // Given
        Long productId = 1L;
        Product originalProduct = buildProduct("CODE-001", "운동화", "나이키", 30000L);
        Product changedProduct = buildProduct("CODE-001", "에어포스 1", "나이키", 35000L);
        ProductDto changedProductDto = ProductDto.fromEntity(changedProduct);

        given(productRepository.findById(productId)).willReturn(Optional.of(originalProduct));

        // When
        Long changedProductId = productService.updateProduct(productId, changedProductDto);

        // Then
        assertThat(changedProductId).isEqualTo(1L);
        assertThat(originalProduct.getCode()).isEqualTo(changedProduct.getCode());
        assertThat(originalProduct.getName()).isEqualTo(changedProduct.getName());
        assertThat(originalProduct.getBrand()).isEqualTo(changedProduct.getBrand());
        assertThat(originalProduct.getPrice()).isEqualTo(changedProduct.getPrice());
        then(productRepository).should().findById(productId);
    }

    @DisplayName("[Service] 상품 수정 - 실패 (상품 ID를 전달하지 않으면 수정을 중단하고 결과를 null로 리턴)")
    @Test
    void updateProduct_fail1() {
        // Given
        Long productId = null;
        Product product = buildProduct("CODE-001", "운동화", "나이키", 30000L);

        // When
        Long changedProductId = productService.updateProduct(productId, ProductDto.fromEntity(product));

        // Then
        assertThat(changedProductId).isEqualTo(null);
        then(productRepository).shouldHaveNoInteractions();
    }

    @DisplayName("[Service] 상품 수정 - 실패 (상품 ID만 전달하고 수정할 정보를 전달하지 않으면 수정을 중단하고 결과를 null로 리턴)")
    @Test
    void updateProduct_fail2() {
        // Given
        Long productId = 1L;

        // When
        Long changedProductId = productService.updateProduct(productId, null);

        // Then
        assertThat(changedProductId).isEqualTo(null);
        then(productRepository).shouldHaveNoInteractions();
    }

    @DisplayName("[Service] 상품 수정 - 실패 (전달한 상품 ID에 해당하는 상품이 존재하지 않는다면 예외를 발생시킴)")
    @Test
    void updateProduct_fail3() {
        // Given
        Long productId = 2L;
        Product originalProduct = buildProduct("CODE-001", "운동화", "나이키", 30000L);
        given(productRepository.findById(productId)).willReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(() -> productService.updateProduct(productId, ProductDto.fromEntity(originalProduct)));

        // Then
        assertThat(thrown)
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorCode.ENTITY_NOT_FOUND.getMessage());
        then(productRepository).should().findById(productId);
    }

    @DisplayName("[Service] 상품 삭제 - 성공")
    @Test
    void deleteProduct_success() {
        // Given
        long productId = 1L;
        willDoNothing().given(productRepository).deleteById(productId);

        // When
        Long deletedProductId = productService.deleteProduct(productId);

        // Then
        assertThat(deletedProductId).isEqualTo(productId);
        then(productRepository).should().deleteById(productId);
    }

    @DisplayName("[Service] 상품 삭제 - 실패 (상품 ID를 전달하지 않으면 상품 삭제를 중단하고 결과로 null를 리턴)")
    @Test
    void deleteProduct_fail1() {
        // Given

        // When
        Long deletedProductId = productService.deleteProduct(null);

        // Then
        assertThat(deletedProductId).isNull();
        then(productRepository).shouldHaveNoInteractions();
    }

    @DisplayName("[Service] 상품 삭제 - 실패 (상품 삭제 중 데이터 오류가 발생하면 쇼핑몰 프로젝트의 기본 예외로 전환하여 예외를 던진다)")
    @Test
    void deleteProduct_fail2() {
        // Given
        long productId = 0L;
        RuntimeException ex = new RuntimeException("Test Exception");
        willThrow(ex).given(productRepository).deleteById(productId);

        // When
        Throwable thrown = catchThrowable(() -> productService.deleteProduct(productId));

        // Then
        assertThat(thrown)
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());
        then(productRepository).should().deleteById(productId);
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