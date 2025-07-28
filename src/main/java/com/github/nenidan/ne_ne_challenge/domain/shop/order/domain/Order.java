package com.github.nenidan.ne_ne_challenge.domain.shop.order.domain;

import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopException;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.vo.OrderDetail;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.OrderId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;

import lombok.Getter;

@Getter
public class Order {

    private OrderId orderId;
    private final UserId userid;
    private final OrderDetail orderDetail;
    private OrderStatus orderStatus;
    private boolean canceled = false;

    private Order(UserId userid, OrderDetail orderDetail) {
        this.userid = userid;
        this.orderDetail = orderDetail;
    }

    public static Order create(UserId userid, OrderDetail orderDetail) {
        return new Order(userid, orderDetail);
    }

    public Order(OrderId orderId, UserId userid, OrderDetail orderDetail, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.userid = userid;
        this.orderDetail = orderDetail;
        this.orderStatus = orderStatus;
    }

    public void cancel() {
        if (isCanceled()) {
            throw new ShopException(ShopErrorCode.ORDER_ALREADY_CANCELED);
        }
        orderStatus = OrderStatus.CANCELED;
        canceled = true;
    }
}

