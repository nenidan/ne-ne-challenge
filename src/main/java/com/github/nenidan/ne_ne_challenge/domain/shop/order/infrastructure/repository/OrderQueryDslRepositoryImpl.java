package com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.entity.OrderEntity;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.entity.QOrderDetailEntity;
import com.github.nenidan.ne_ne_challenge.domain.shop.order.infrastructure.entity.QOrderEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderQueryDslRepositoryImpl implements OrderQueryDslRepository{

    private final JPAQueryFactory queryFactory;

    public List<OrderEntity> findAllOrdersBy(Long userId, Long cursor, String keyword, int limit ) {
        QOrderEntity orderEntity = QOrderEntity.orderEntity;
        QOrderDetailEntity orderDetailEntity = QOrderDetailEntity.orderDetailEntity;

        BooleanExpression cursorExpression = cursor == null ? null : orderEntity.id.loe(cursor);
        BooleanExpression keywordExpression = keyword == null ? null : orderDetailEntity.productName.like("%" + keyword + "%");

        return queryFactory.selectFrom(orderEntity)
            .distinct()
            .join(orderEntity.orderDetailEntity, orderDetailEntity)
            .where(
                orderEntity.userId.eq(userId),
                cursorExpression,
                keywordExpression
            )
            .orderBy(orderEntity.id.desc())
            .limit(limit)
            .fetch();
    }
}
