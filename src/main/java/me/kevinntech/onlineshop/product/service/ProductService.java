package me.kevinntech.onlineshop.product.service;

import lombok.RequiredArgsConstructor;
import me.kevinntech.onlineshop.base.BusinessException;
import me.kevinntech.onlineshop.base.ErrorCode;
import me.kevinntech.onlineshop.product.Product;
import me.kevinntech.onlineshop.product.dto.ProductDto;
import me.kevinntech.onlineshop.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public Long createProduct(ProductDto dto) {
        if (dto == null) {
            return null;
        }

        Product savedProduct = productRepository.save(dto.toEntity());
        return savedProduct.getId();
    }

    public List<ProductDto> getProductsOrderById() {
        return productRepository.findProductsOrderById()
                .stream()
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList());
    }

}
