package me.kevinntech.onlineshop.domain.warehousing;

import lombok.RequiredArgsConstructor;
import me.kevinntech.onlineshop.domain.product.dto.ProductDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/warehousing")
@Controller
public class WarehousingViewController {

    private final WarehousingService warehousingService;

    @GetMapping("/create")
    public String showsFormForCreation(Model model) {
        List<ProductDto> products = warehousingService.getProductsOrderById();
        model.addAttribute("products", products);

        return "warehousing/create";
    }

}