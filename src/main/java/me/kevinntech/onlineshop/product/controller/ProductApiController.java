package me.kevinntech.onlineshop.product.controller;

import lombok.RequiredArgsConstructor;
import me.kevinntech.onlineshop.base.dto.OkResponse;
import me.kevinntech.onlineshop.product.dto.CreateProductRequest;
import me.kevinntech.onlineshop.product.dto.ValidateProductCodeRequest;
import me.kevinntech.onlineshop.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class ProductApiController {

    private final ProductService productService;

    @PostMapping("/api/v1/products")
    public OkResponse<Long> save(@Valid @RequestBody CreateProductRequest request) {
        Long productId = productService.createProduct(request.toDto());
        return OkResponse.of(productId);
    }

    @PostMapping("/api/v1/products/validate-product-code")
    public OkResponse<String> validateProductCode(@Valid @RequestBody ValidateProductCodeRequest request) {
        return OkResponse.of("ok");
    }

}
