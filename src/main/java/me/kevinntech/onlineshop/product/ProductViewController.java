package me.kevinntech.onlineshop.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/products")
@Controller
public class ProductViewController {

    @GetMapping("/create")
    public String showsFormForCreation(Model model) {
        model.addAttribute("createProductForm", new CreateProductForm());
        return "products/create";
    }

}
