package com.github.nenidan.ne_ne_challenge.domain.shop.repository;

import com.github.nenidan.ne_ne_challenge.domain.shop.entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("""
        SELECT p FROM Product p WHERE (:keyword IS NULL OR p.productName LIKE CONCAT(:keyword, '%'))
    """)
    Page<Product> findAllProducts(Pageable pageable,  String keyword);
}