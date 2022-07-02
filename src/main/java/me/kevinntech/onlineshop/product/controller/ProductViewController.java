package me.kevinntech.onlineshop.product.controller;

import lombok.RequiredArgsConstructor;
import me.kevinntech.onlineshop.product.dto.CreateProductForm;
import me.kevinntech.onlineshop.product.dto.GetProductResponse;
import me.kevinntech.onlineshop.product.dto.ProductDto;
import me.kevinntech.onlineshop.product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/products")
@Controller
public class ProductViewController {

    private final ProductService productService;

    @GetMapping("/create")
    public String showsFormForCreation(Model model) {
        model.addAttribute("createProductForm", new CreateProductForm());
        return "products/create";
    }

    @GetMapping("/list")
    public String getProducts(Model model) {
        List<GetProductResponse> products = productService.getProductsOrderById()
                .stream()
                .map(GetProductResponse::fromDto)
                .collect(Collectors.toList());
        model.addAttribute("products", products);

        return "products/list";
    }

}
