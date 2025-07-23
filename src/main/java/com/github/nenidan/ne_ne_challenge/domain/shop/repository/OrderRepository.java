package com.github.nenidan.ne_ne_challenge.domain.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.shop.dto.OrderFlatProjection;
import com.github.nenidan.ne_ne_challenge.domain.shop.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query(value = "SELECT DISTINCT " +
        "o.id AS order_id," +
        " o.user_id, o.status," +
        " od.price_at_order," +
        "p.id AS product_id," +
        " p.product_name," +
        " p.product_description," +
        " o.created_at," +
        " o.updated_at," +
        " o.deleted_at " +
        "FROM orders o " +
        "JOIN order_detail od ON o.id = od.order_id " +
        "JOIN product p ON od.product_id = p.id " +
        "WHERE o.user_id = :userId " +
        "AND (:cursor IS NULL OR o.id <= :cursor) " +
        "AND (:keyword IS NULL OR p.product_name LIKE CONCAT('%', :keyword, '%')) " +
        "ORDER BY o.id DESC " +
        "LIMIT :limit",
        nativeQuery = true)
    List<OrderFlatProjection> findByKeyword(
        @Param("userId") Long userId,
        @Param("cursor") Long cursor,
        @Param("keyword") String keyword,
        @Param("limit") int limit
    );
}
