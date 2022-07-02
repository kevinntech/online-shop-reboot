package me.kevinntech.onlineshop.product.service;

import lombok.RequiredArgsConstructor;
import me.kevinntech.onlineshop.product.Product;
import me.kevinntech.onlineshop.product.dto.ProductDto;
import me.kevinntech.onlineshop.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public Long createProduct(ProductDto dto) {
        Product savedProduct = productRepository.save(dto.toEntity());
        return savedProduct.getId();
    }

    @Transactional(readOnly = true)
    public boolean validateProductCode(String productCode) {
        boolean existsByProductCode = productRepository.existsByCode(productCode);

        if (existsByProductCode) {
            throw new IllegalArgumentException("잘못된 상품 코드입니다.");
        }

        return true;
    }

}
