package com.github.nenidan.ne_ne_challenge.domain.shop.stock.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.nenidan.ne_ne_challenge.domain.shop.stock.infrastructure.entity.StockEntity;

public interface StockJpaRepository extends JpaRepository<StockEntity, Long> {
    @Query("SELECT se FROM StockEntity se WHERE se.productId = :productId")
    Optional<StockEntity> findByProductId(@Param("productId") Long productId);
}
