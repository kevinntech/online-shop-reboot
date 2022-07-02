package me.kevinntech.onlineshop.product.controller;

import lombok.RequiredArgsConstructor;
import me.kevinntech.onlineshop.product.service.ProductService;
import me.kevinntech.onlineshop.product.dto.CreateProductRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class ProductApiController {

    private final ProductService productService;

    // TODO:공통 예외 처리를 하는 부분이 추가되면 변경 필요
    @PostMapping("/api/v1/products")
    public ResponseEntity save(@Valid @RequestBody CreateProductRequest request) {
        productService.createProduct(request.toDto());

        return ResponseEntity.ok().build();
    }

}
