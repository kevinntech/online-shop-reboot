package me.kevinntech.onlineshop.domain.warehousing.repository;

import me.kevinntech.onlineshop.domain.warehousing.Warehousing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface WarehousingRepository extends JpaRepository<Warehousing, Long> {
}