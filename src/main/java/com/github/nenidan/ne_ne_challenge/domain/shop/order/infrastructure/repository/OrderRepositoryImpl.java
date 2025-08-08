package com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.exception.OrderErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.exception.OrderException;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.model.Order;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.repository.OrderRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.entity.OrderEntity;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.mapper.OrderMapper;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.OrderId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderQueryDslRepository orderQueryDslRepository;

    @Override
    public Order save(Order order) {
        OrderEntity entity = OrderMapper.toEntity(order);
        OrderEntity saveOrder = orderJpaRepository.save(entity);
        return OrderMapper.toDomain(saveOrder);
    }

    @Override
    public Order findByUserIdAndOrderId(UserId userId, OrderId orderId) {
        OrderEntity orderEntity = orderJpaRepository.findByUserIdAndId(userId.getValue(), orderId.getValue())
            .orElseThrow(() -> new OrderException(OrderErrorCode.ORDER_NOT_FOUND));
        return OrderMapper.toDomain(orderEntity);
    }

    @Override
    public List<Order> findAllOrders(UserId userId, Long cursor, String keyword, int size) {
        return orderQueryDslRepository.findAllOrdersBy(userId.getValue(), cursor, keyword, size)
            .stream()
            .map(OrderMapper::toDomain)
            .toList();
    }

    @Override
    public List<Order> findAllOrders() {
        return orderJpaRepository.findAll()
            .stream()
            .map(OrderMapper::toDomain)
            .toList();
    }
}
