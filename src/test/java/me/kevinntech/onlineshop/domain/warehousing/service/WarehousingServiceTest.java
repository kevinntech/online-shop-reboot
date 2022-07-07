package me.kevinntech.onlineshop.domain.warehousing.service;

import me.kevinntech.onlineshop.domain.product.Product;
import me.kevinntech.onlineshop.domain.product.service.ProductService;
import me.kevinntech.onlineshop.domain.stock.Stock;
import me.kevinntech.onlineshop.domain.stock.StockRepository;
import me.kevinntech.onlineshop.domain.warehousing.Warehousing;
import me.kevinntech.onlineshop.domain.warehousing.dto.CreateWarehousingDto;
import me.kevinntech.onlineshop.domain.warehousing.repository.WarehousingRepository;
import me.kevinntech.onlineshop.global.error.ErrorCode;
import me.kevinntech.onlineshop.global.error.exception.GeneralException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("비즈니스 로직 - 상품")
@ExtendWith(MockitoExtension.class)
class WarehousingServiceTest {

    @InjectMocks WarehousingService warehousingService;
    @Mock ProductService productService;
    @Mock WarehousingRepository warehousingRepository;
    @Mock StockRepository stockRepository;

    @DisplayName("[Service] 입고 등록 - 성공")
    @Test
    void createWarehousing_success() {
        // Given
        Product product = buildProduct("CODE-001", "운동화", "나이키", 30000L);
        Long quantity = 10L;
        Stock stock = buildStock(product.getPrice(), quantity);
        Warehousing warehousing = buildWarehousing(product.getPrice(), quantity, product, stock);

        given(productService.findById(product.getId())).willReturn(Optional.of(product));
        given(stockRepository.existsStockByProductId(product.getId())).willReturn(true);
        given(stockRepository.findStockByProductId(product.getId())).willReturn(stock);
        given(warehousingRepository.save(any(Warehousing.class))).willReturn(warehousing);

        CreateWarehousingDto dto = CreateWarehousingDto.builder()
                .productId(product.getId())
                .price(product.getPrice())
                .quantity(quantity)
                .build();

        // When
        Long warehousingId = warehousingService.createWarehousing(dto);

        // Then
        assertThat(warehousingId).isEqualTo(1L);
        then(productService).should().findById(product.getId());
        then(stockRepository).should().existsStockByProductId(product.getId());
        then(stockRepository).should().findStockByProductId(product.getId());
        then(warehousingRepository).should().save(any(Warehousing.class));
    }

    @DisplayName("[Service] 입고 등록 - 실패 (존재하지 않는 상품에 대한 입고 등록을 시도하면 예외를 발생시킨다)")
    @Test
    void createWarehousing_fail() {
        // Given
        Long productId = 0L;
        given(productService.findById(productId)).willReturn(Optional.empty());

        CreateWarehousingDto dto = CreateWarehousingDto.builder()
                .productId(productId)
                .price(30000)
                .quantity(10)
                .build();

        // When
        Throwable thrown = catchThrowable(() -> warehousingService.createWarehousing(dto));

        // Then
        assertThat(thrown)
                .isInstanceOf(GeneralException.class)
                .hasMessageContaining(ErrorCode.ENTITY_NOT_FOUND.getMessage());
        then(productService).should().findById(productId);
        then(stockRepository).shouldHaveNoInteractions();
        then(stockRepository).shouldHaveNoInteractions();
        then(warehousingRepository).shouldHaveNoInteractions();
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

    private Stock buildStock(Long price, Long quantity) {
        Stock stock = Stock.builder()
                .price(price)
                .quantity(quantity)
                .build();

        ReflectionTestUtils.setField(stock, "id", 1L);
        return stock;
    }

    private Warehousing buildWarehousing(Long price, Long quantity, Product product, Stock stock) {
        Warehousing warehousing = Warehousing.builder()
                .price(price)
                .quantity(quantity)
                .build();
        warehousing.establishRelationship(product, stock);

        ReflectionTestUtils.setField(warehousing, "id", 1L);
        return warehousing;
    }
}