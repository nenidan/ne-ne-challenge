package com.github.nenidan.ne_ne_challenge.domain.shop.repository;

import com.github.nenidan.ne_ne_challenge.domain.shop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}