package me.kevinntech.onlineshop.product.service;

import lombok.RequiredArgsConstructor;
import me.kevinntech.onlineshop.base.BusinessException;
import me.kevinntech.onlineshop.base.ErrorCode;
import me.kevinntech.onlineshop.product.Product;
import me.kevinntech.onlineshop.product.dto.ProductDto;
import me.kevinntech.onlineshop.product.dto.UpdateProductRequest;
import me.kevinntech.onlineshop.product.repository.ProductRepository;
import me.kevinntech.onlineshop.product.validator.UniqueProductCodeValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UniqueProductCodeValidator uniqueProductCodeValidator;

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

    public Optional<ProductDto> findProductById(Long productId) {
        if (productId == null) {
            return Optional.empty();
        }

        return productRepository.findById(productId)
                .map(ProductDto::fromEntity);
    }

    public Long updateProduct(Long productId, ProductDto dto) {
        if (productId == null || dto == null) {
            return null;
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ENTITY_NOT_FOUND));

        updateProductOrThrow(dto, product);
        return product.getId();
    }

    private void updateProductOrThrow(ProductDto dto, Product product) {
        if (isChangeableProductCode(dto, product)) {
            product.update(dto);
        } else {
            throw new BusinessException(ErrorCode.PRODUCT_CODE_DUPLICATION);
        }
    }

    private boolean isChangeableProductCode(ProductDto dto, Product product) {
        if (productRepository.existsByCode(dto.getCode())) {
            if (product.getCode().equals(dto.getCode())) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public Long deleteProduct(Long productId) {
        if (productId == null) {
            return null;
        }

        productRepository.deleteById(productId);
        return productId;
    }
}
