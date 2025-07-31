package com.github.nenidan.ne_ne_challenge.domain.shop.order.application;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto.OrderResult;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.Order;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.vo.OrderDetail;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.repository.OrderRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.OrderId;
import com.github.nenidan.ne_ne_challenge.domain.shop.stock.domain.event.StockUpdateEvent;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;
import com.github.nenidan.ne_ne_challenge.global.dto.CursorResponse;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public OrderService(OrderRepository orderRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.orderRepository = orderRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * 주문을 생성하고, 재고 감소 이벤트를 발행합니다.
     *
     * @param userId 주문을 생성한 사용자 ID
     * @param orderDetail 주문 상세 정보 (상품, 수량 등)
     * @return 생성된 주문 결과 DTO
     * @author kimyongjun0129
     */
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

    /**
     * 주문을 취소합니다.
     *
     * @param orderId 취소할 주문 ID
     * @author kimyongjun0129
     */
    @Transactional
    public void cancelOrder(OrderId orderId) {
        Order order = orderRepository.findOrder(orderId);
        order.cancel();
        orderRepository.cancelOrder(orderId);
    }

    /**
     * 단일 주문을 조회합니다.
     *
     * @param orderId 조회할 주문 ID
     * @return 주문 결과 DTO
     * @author kimyongjun0129
     */
    @Transactional(readOnly = true)
    public OrderResult findOrder(OrderId orderId) {
        Order order = orderRepository.findOrder(orderId);

        return OrderResult.fromEntity(order);
    }

    /**
     * 특정 사용자에 대한 주문 목록을 커서 기반으로 조회합니다.
     *
     * @param userId 사용자 ID
     * @param cursor 마지막으로 조회한 주문 ID (페이징용)
     * @param size 조회할 개수
     * @param keyword 검색 키워드
     * @return 커서 응답 (주문 리스트, 다음 커서 값, 다음 페이지 여부)
     * @author kimyongjun0129
     */
    @Transactional(readOnly = true)
    public CursorResponse<OrderResult, Long> findAllOrder(UserId userId, Long cursor, int size, String keyword) {

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
