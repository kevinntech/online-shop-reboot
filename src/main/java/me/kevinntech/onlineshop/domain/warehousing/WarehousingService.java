package me.kevinntech.onlineshop.domain.warehousing;

import lombok.RequiredArgsConstructor;
import me.kevinntech.onlineshop.domain.product.dto.ProductDto;
import me.kevinntech.onlineshop.domain.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WarehousingService {

    private final ProductService productService;

    public List<ProductDto> getProductsOrderById() {
        return productService.getProductsOrderById();
    }

}
