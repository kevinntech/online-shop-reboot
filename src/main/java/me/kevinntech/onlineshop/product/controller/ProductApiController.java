package me.kevinntech.onlineshop.product.controller;

import lombok.RequiredArgsConstructor;
import me.kevinntech.onlineshop.base.dto.OkResponse;
import me.kevinntech.onlineshop.product.dto.CreateProductRequest;
import me.kevinntech.onlineshop.product.dto.GetProductResponse;
import me.kevinntech.onlineshop.product.dto.UpdateProductRequest;
import me.kevinntech.onlineshop.product.dto.ValidateProductCodeRequest;
import me.kevinntech.onlineshop.product.service.ProductService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RequiredArgsConstructor
@RestController
public class ProductApiController {

    private final ProductService productService;

    @PostMapping("/api/v1/products")
    public OkResponse<Long> createProduct(@Valid @RequestBody CreateProductRequest request) {
        Long productId = productService.createProduct(request.toDto());
        return OkResponse.of(productId);
    }

    @PostMapping("/api/v1/products/validate-product-code")
    public OkResponse<String> validateProductCode(@Valid @RequestBody ValidateProductCodeRequest request) {
        return OkResponse.of("ok");
    }

    @GetMapping("/api/v1/products")
    public OkResponse<List<GetProductResponse>> getProducts() {
        List<GetProductResponse> products = productService.getProductsOrderById()
                .stream()
                .map(GetProductResponse::fromDto)
                .collect(Collectors.toList());

        return OkResponse.of(products);
    }

    @PutMapping("/api/v1/products/{productId}")
    public OkResponse<Long> updateProduct(@PathVariable Long productId,
                                          @Valid @RequestBody UpdateProductRequest request) {
        Long updatedProductId = productService.updateProduct(productId, request.toDto());
        return OkResponse.of(updatedProductId);
    }

    @DeleteMapping("/api/v1/products/{productId}")
    public OkResponse<Long> deleteProduct(@Positive @PathVariable Long productId) {
        Long deletedProductId = productService.deleteProduct(productId);
        return OkResponse.of(deletedProductId);
    }

}
