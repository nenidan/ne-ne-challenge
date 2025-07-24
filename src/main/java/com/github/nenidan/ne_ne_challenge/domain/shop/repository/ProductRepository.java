package com.github.nenidan.ne_ne_challenge.domain.shop.repository;

import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.shop.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT * FROM product p " +
        "WHERE (:cursor IS NULL OR id <= :cursor) " +
        "AND (:keyword IS NULL OR p.product_name LIKE CONCAT('%', :keyword, '%')) " +
        "ORDER BY p.id DESC " +
        "LIMIT :limit",
        nativeQuery = true)
    List<Product> findByKeyword(
        @Param("cursor") Long cursor,
        @Param("keyword") String keyword,
        @Param("limit") int limit
    );
}