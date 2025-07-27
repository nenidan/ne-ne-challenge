package com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure;

import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.infrastructure.entity.ProductEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
    @Query(value = "SELECT * FROM product p " +
        "WHERE p.deleted_at IS NULL " +
        "AND (:cursor IS NULL OR id <= :cursor) " +
        "AND (:keyword IS NULL OR p.product_name LIKE CONCAT('%', :keyword, '%')) " +
        "ORDER BY p.id DESC " +
        "LIMIT :limit",
        nativeQuery = true)
    List<ProductEntity> findAllByKeywordAndCursor(
        @Param("cursor") Long cursor,
        @Param("keyword") String keyword,
        @Param("limit") int limit
    );
}