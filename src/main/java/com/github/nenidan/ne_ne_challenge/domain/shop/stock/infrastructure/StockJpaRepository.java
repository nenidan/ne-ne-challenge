package com.github.nenidan.ne_ne_challenge.domain.shop.stock.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.nenidan.ne_ne_challenge.domain.shop.stock.infrastructure.entity.StockEntity;

public interface StockJpaRepository extends JpaRepository<StockEntity, Long> {
}
