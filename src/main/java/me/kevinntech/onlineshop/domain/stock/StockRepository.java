package me.kevinntech.onlineshop.domain.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findStockByProductId(Long productId);

    boolean existsStockByProductId(Long productId);

}
