package me.kevinntech.onlineshop.product.service;

import lombok.RequiredArgsConstructor;
import me.kevinntech.onlineshop.base.GeneralException;
import me.kevinntech.onlineshop.base.ErrorCode;
import me.kevinntech.onlineshop.product.Product;
import me.kevinntech.onlineshop.product.dto.ProductDto;
import me.kevinntech.onlineshop.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
                .orElseThrow(() -> new GeneralException(ErrorCode.ENTITY_NOT_FOUND));

        updateProductOrThrow(dto, product);
        return product.getId();
    }

    private void updateProductOrThrow(ProductDto dto, Product product) {
        if (isChangeableProductCode(dto, product)) {
            product.update(dto);
        } else {
            throw new GeneralException(ErrorCode.PRODUCT_CODE_DUPLICATION);
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
        try {
            return deleteProductById(productId);
        } catch (Exception ex) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, ex);
        }
    }

    private Long deleteProductById(Long productId) {
        if (productId == null) {
            return null;
        }

        productRepository.deleteById(productId);
        return productId;
    }
}
