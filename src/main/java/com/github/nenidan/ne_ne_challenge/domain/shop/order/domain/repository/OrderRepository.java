package com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.repository;

import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.model.Order;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.OrderId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;

public interface OrderRepository {
    Order save(Order order);
    Order findByUserIdAndOrderId(UserId userId, OrderId orderId);
    List<Order> findAllOrders(UserId userId, Long cursor, String keyword, int size);
}
