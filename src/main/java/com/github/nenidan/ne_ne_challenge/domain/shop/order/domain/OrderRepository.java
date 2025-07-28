package com.github.nenidan.ne_ne_challenge.domain.shop.order.domain;

import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.shop.vo.OrderId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;

public interface OrderRepository {
    Order createOrder(Order order);
    void cancelOrder(OrderId orderId);
    Order findOrder(OrderId orderId);
    List<Order> findAllOrders(UserId userId, Long cursor, String keyword, int size);
}
