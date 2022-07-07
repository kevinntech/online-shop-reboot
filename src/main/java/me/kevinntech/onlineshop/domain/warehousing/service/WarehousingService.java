package me.kevinntech.onlineshop.domain.warehousing.service;

import lombok.RequiredArgsConstructor;
import me.kevinntech.onlineshop.domain.product.Product;
import me.kevinntech.onlineshop.domain.product.dto.ProductDto;
import me.kevinntech.onlineshop.domain.product.service.ProductService;
import me.kevinntech.onlineshop.domain.stock.Stock;
import me.kevinntech.onlineshop.domain.stock.StockRepository;
import me.kevinntech.onlineshop.domain.warehousing.Warehousing;
import me.kevinntech.onlineshop.domain.warehousing.dto.CreateWarehousingDto;
import me.kevinntech.onlineshop.domain.warehousing.repository.WarehousingRepository;
import me.kevinntech.onlineshop.global.error.ErrorCode;
import me.kevinntech.onlineshop.global.error.exception.GeneralException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class WarehousingService {

    private final ProductService productService;
    private final WarehousingRepository warehousingRepository;
    private final StockRepository stockRepository;

    public List<ProductDto> getProductsOrderById() {
        return productService.getProductsOrderById();
    }

    public Long createWarehousing(CreateWarehousingDto dto) {
        Product product = getProductOrThrow(dto);
        Stock stock = getSavedStock(product, dto);
        Warehousing createdWarehousing = createWarehousingAndReturn(dto, product, stock);
        return createdWarehousing.getId();
    }

    private Product getProductOrThrow(CreateWarehousingDto dto) {
        Product product = productService.findById(dto.getProductId())
                .orElseThrow(() -> new GeneralException(ErrorCode.ENTITY_NOT_FOUND));
        return product;
    }

    private Stock getSavedStock(Product product, CreateWarehousingDto dto) {
        Long productId = product.getId();

        if (stockRepository.existsStockByProductId(productId)) {
            return stockRepository.findStockByProductId(productId);
        } else {
            Stock stock = Stock.builder()
                    .price(dto.getPrice())
                    .build();
            stock.changeProduct(product);
            return stockRepository.save(stock);
        }
    }

    private Warehousing createWarehousingAndReturn(CreateWarehousingDto dto, Product product, Stock stock) {
        Warehousing warehousing = buildWarehousing(dto);
        warehousing.establishRelationship(product, stock);
        Warehousing savedWarehousing = warehousingRepository.save(warehousing);
        return savedWarehousing;
    }

    private Warehousing buildWarehousing(CreateWarehousingDto dto) {
        Warehousing warehousing = Warehousing.builder()
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .build();
        return warehousing;
    }

}
