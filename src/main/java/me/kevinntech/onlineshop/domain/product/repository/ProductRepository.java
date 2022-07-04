package me.kevinntech.onlineshop.domain.product.repository;

import me.kevinntech.onlineshop.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByCode(String code);

    @Query("select p from Product p order by p.id")
    List<Product> findProductsOrderById();

    @Query("select p from Product p join p.stock s where s.quantity > 0 order by p.id")
    List<Product> findProductsInStock();

}
