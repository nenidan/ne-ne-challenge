package com.github.nenidan.ne_ne_challenge.domain.shop.order.domain;

import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.vo.OrderId;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.vo.UserId;

public interface OrderRepository {
    Order createOrder(Order order);
    void cancelOrder(OrderId orderId);
    Order findOrder(OrderId orderId);
    List<Order> findAllOrders(UserId userId, Long cursor, String keyword, int size);
}
