package com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.repository;

import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.entity.OrderEntity;

public interface OrderQueryDslRepository {
    List<OrderEntity> findAllOrdersBy(Long userId, Long cursor, String keyword, int limit );
}
