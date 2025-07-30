package com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.mapper;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.Order;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.vo.OrderDetail;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.domain.OrderStatus;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.OrderFlatProjection;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.OrderId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.entity.OrderDetailEntity;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.entity.OrderEntity;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;

public class OrderMapper {

    public static OrderEntity toEntity(Order order) {
        OrderEntity orderEntity = new OrderEntity(
            order.getUserid().getValue(),
            new OrderDetailEntity(
                order.getOrderDetail().getProductId().getValue(),
                order.getOrderDetail().getNameAtOrder(),
                order.getOrderDetail().getDescriptionAtOrder(),
                order.getOrderDetail().getPriceAtOrder(),
                order.getOrderDetail().getQuantity()
            )
        );
        if (order.isCanceled()) {
            orderEntity.delete();
        }
        return orderEntity;
    }

    public static Order toDomain(OrderEntity orderEntity) {
        Order order = new Order(
            new OrderId(orderEntity.getId()),
            new UserId(orderEntity.getUserId()),
            new OrderDetail(
                new ProductId(orderEntity.getOrderDetailEntity().getProductId()),
                orderEntity.getOrderDetailEntity().getProductName(),
                orderEntity.getOrderDetailEntity().getProductDescription(),
                orderEntity.getOrderDetailEntity().getPriceAtOrder(),
                orderEntity.getOrderDetailEntity().getQuantity()
            ),
            orderEntity.getStatus()
        );
        if (orderEntity.getDeletedAt() != null) {
            order.cancel();
        }
        return order;
    }

    public static Order toProjection(OrderFlatProjection orderFlatProjection) {
        return new Order(
            new OrderId(orderFlatProjection.getOrderId()),
            new UserId(orderFlatProjection.getUserId()),
            new OrderDetail(
                new ProductId(orderFlatProjection.getProductId()),
                orderFlatProjection.getProductName(),
                orderFlatProjection.getProductDescription(),
                orderFlatProjection.getPriceAtOrder(),
                orderFlatProjection.getQuantity()
            ),
            OrderStatus.valueOf(orderFlatProjection.getStatus())
        );
    }
}
