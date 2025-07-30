package com.github.nenidan.ne_ne_challenge.domain.shop.order.application;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto.OrderResult;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.Order;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.vo.OrderDetail;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.OrderRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.OrderId;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.event.StockUpdateEvent;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.user.exception.UserException;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public OrderService(OrderRepository orderRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.orderRepository = orderRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public OrderResult createOrder(UserId userId, OrderDetail orderDetail) {
        Order order = Order.create(userId, orderDetail);
        Order saveOrder = orderRepository.createOrder(order);

        // 재고 감소
        applicationEventPublisher.publishEvent(
            new StockUpdateEvent(
                saveOrder.getOrderDetail().getProductId(),
                saveOrder.getOrderDetail().getQuantity()
            )
        );

        return OrderResult.fromEntity(saveOrder);
    }

    @Transactional
    public void cancelOrder(OrderId orderId) {
        Order order = orderRepository.findOrder(orderId);
        order.cancel();
        orderRepository.cancelOrder(orderId);
    }

    @Transactional(readOnly = true)
    public OrderResult findOrder(OrderId orderId) {
        Order order = orderRepository.findOrder(orderId);

        return OrderResult.fromEntity(order);
    }

    @Transactional(readOnly = true)
    public CursorResponse<OrderResult, Long> findAllOrder(UserId userId, Long cursor, int size, String keyword) {
        if(userId == null) {
            throw new UserException(UserErrorCode.USER_NOT_FOUND);
        }

        List<OrderResult> orderList = orderRepository.findAllOrders(userId, cursor, keyword, size+1)
            .stream()
            .map(OrderResult::fromEntity)
            .toList();

        boolean hasNext = orderList.size() > size;

        List<OrderResult> content = hasNext ? orderList.subList(0, size) : orderList;

        Long nextCursor = hasNext ? orderList.get(orderList.size() - 1).getOrderId() : null;

        return new CursorResponse<>(content, nextCursor, orderList.size() > size);
    }
}
