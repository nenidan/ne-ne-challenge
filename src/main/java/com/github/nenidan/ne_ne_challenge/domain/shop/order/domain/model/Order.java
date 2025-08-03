package com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.model;

import java.time.LocalDateTime;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.exception.OrderErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.exception.OrderException;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.type.OrderStatus;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.vo.OrderDetail;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.OrderId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;

import lombok.Getter;

@Getter
public class Order {

    private final OrderId orderId;
    private final UserId userid;
    private final OrderDetail orderDetail;
    private OrderStatus orderStatus;
    private LocalDateTime deletedAt;

    public Order(OrderId orderId, UserId userid, OrderDetail orderDetail, OrderStatus orderStatus, LocalDateTime deletedAt) {
        this.orderId = orderId;
        this.userid = userid;
        this.orderDetail = orderDetail;
        this.orderStatus = orderStatus;
        this.deletedAt = deletedAt;
    }

    public void isCanceled() {
        if (deletedAt != null) {
            throw new OrderException(OrderErrorCode.ORDER_ALREADY_CANCELED);
        }
    }

    public void cancel() {
        orderStatus = OrderStatus.CANCELED;
        this.deletedAt = LocalDateTime.now();
        orderDetail.delete();
    }

    public void revertCancel() {
        if (!OrderStatus.CANCELED.equals(orderStatus)) {
            throw new OrderException(OrderErrorCode.ORDER_NOT_CANCELED_FOR_RECOVERY);
        }
        orderStatus = OrderStatus.CONFIRM;
        this.deletedAt = null;
        orderDetail.revertCancel();
    }
}

