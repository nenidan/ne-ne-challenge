package com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.Order;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.exception.OrderErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.exception.OrderException;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.repository.OrderRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.mapper.OrderMapper;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.OrderId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.entity.OrderEntity;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    public OrderRepositoryImpl(OrderJpaRepository orderJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
    }

    @Override
    public Order createOrder(Order order) {
        OrderEntity entity = OrderMapper.toEntity(order);
        OrderEntity saveOrder = orderJpaRepository.save(entity);
        return OrderMapper.toDomain(saveOrder);
    }

    @Override
    public void cancelOrder(OrderId orderId) {
        OrderEntity orderEntity = orderJpaRepository.findById(orderId.getValue())
            .orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_NOT_FOUND));
        orderEntity.delete();
    }

    @Override
    public Order findOrder(OrderId orderId) {
        OrderEntity orderEntity = orderJpaRepository.findById(orderId.getValue())
            .orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_NOT_FOUND));
        return OrderMapper.toDomain(orderEntity);
    }

    @Override
    public List<Order> findAllOrders(UserId userId, Long cursor, String keyword, int size) {
        return orderJpaRepository.findByKeyword(userId.getValue(), cursor, keyword, size)
            .stream()
            .map(OrderMapper::toProjection)
            .toList();
    }
}
