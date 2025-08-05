package com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.repository;

import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.mapper.OrderFlatProjection;

public interface OrderQueryDslRepository {
    List<OrderFlatProjection> findAllOrdersBy(Long userId, Long cursor, String keyword, int limit );
}
