package com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.entity.OrderEntity;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {

    @Query(value = "SELECT DISTINCT " +
        " o.id AS order_id, "+
        " o.user_id," +
        " o.status," +
        "od.product_id, " +
        " od.product_name, " +
        " od.product_description, " +
        " od.price_at_order " +
        "FROM orders o " +
        "JOIN order_detail_entity od ON o.id = od.order_id " +
        "WHERE o.user_id = :userId " +
        "AND (:cursor IS NULL OR o.id <= :cursor) " +
        "AND (:keyword IS NULL OR od.product_name LIKE CONCAT('%', :keyword, '%')) " +
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
