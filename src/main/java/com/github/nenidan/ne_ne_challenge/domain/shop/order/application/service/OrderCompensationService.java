package com.github.nenidan.ne_ne_challenge.domain.shop.order.application.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.model.Order;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.repository.OrderRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.event.StockConvertRestoreEvent;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.OrderId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderCompensationService {

    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void compensateOrderCancel (UserId userId, OrderId orderId) {
        Order order = orderRepository.findByUserIdAndOrderId(userId, orderId);
        order.revertCancel();
        orderRepository.save(order);
        applicationEventPublisher.publishEvent(
            new StockConvertRestoreEvent(
                order.getOrderDetail().getProductId(),
                order.getOrderDetail().getQuantity()
            )
        );
    }
}
