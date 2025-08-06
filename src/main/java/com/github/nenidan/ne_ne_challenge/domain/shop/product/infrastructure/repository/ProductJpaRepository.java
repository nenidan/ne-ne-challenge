package com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.entity.ProductEntity;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
}