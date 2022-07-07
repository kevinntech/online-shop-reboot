package me.kevinntech.onlineshop.domain.base;

import lombok.RequiredArgsConstructor;
import me.kevinntech.onlineshop.domain.product.dto.ProductDto;
import me.kevinntech.onlineshop.domain.product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BaseController {

    private final ProductService productService;

    @GetMapping("/")
    public String showsIndex(Model model) {
        List<ProductDto> products = productService.getProductsInStock();
        model.addAttribute("products", products);

        return "index";
    }

}
