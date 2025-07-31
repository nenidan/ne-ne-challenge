package com.github.nenidan.ne_ne_challenge.domain.shop.order.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopException;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto.OrderResponse;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto.OrderedProduct;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.Order;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.OrderRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.vo.OrderDetail;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.vo.OrderId;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.vo.UserId;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderResponse createOrder(UserId userId, OrderedProduct orderedProduct) {

        OrderDetail orderDetail = new OrderDetail(
            orderedProduct.getProductId(),
            orderedProduct.getProductName(),
            orderedProduct.getProductDescription(),
            orderedProduct.getPriceAtOrder()
        );
        Order order = Order.create(userId, orderDetail);
        Order saveOrder = orderRepository.createOrder(order);

        return OrderResponse.fromEntity(saveOrder);
    }

    @Transactional
    public void cancelOrder(OrderId orderId) {
        Order order = orderRepository.findOrder(orderId);
        order.cancel();
        orderRepository.cancelOrder(orderId);
    }

    @Transactional(readOnly = true)
    public OrderResponse findOrder(OrderId orderId) {
        Order order = orderRepository.findOrder(orderId);

        return OrderResponse.fromEntity(order);
    }

    @Transactional(readOnly = true)
    public CursorResponse<OrderResponse, Long> findAllOrder(UserId userId, Long cursor, int size, String keyword) {
        if(userId == null) {
            throw new ShopException(ShopErrorCode.USER_NOT_FOUND);
        }

        List<OrderResponse> orderList = orderRepository.findAllOrders(userId, cursor, keyword, size+1)
            .stream()
            .map(OrderResponse::fromEntity)
            .toList();

        boolean hasNext = orderList.size() > size;

        List<OrderResponse> content = hasNext ? orderList.subList(0, size) : orderList;

        Long nextCursor = hasNext ? orderList.get(orderList.size() - 1).getOrderId() : null;

        return new CursorResponse<>(content, nextCursor, orderList.size() > size);
    }
}
