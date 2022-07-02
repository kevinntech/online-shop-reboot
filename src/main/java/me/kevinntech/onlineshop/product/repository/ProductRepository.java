package me.kevinntech.onlineshop.product.repository;

import me.kevinntech.onlineshop.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByCode(String code);

}
