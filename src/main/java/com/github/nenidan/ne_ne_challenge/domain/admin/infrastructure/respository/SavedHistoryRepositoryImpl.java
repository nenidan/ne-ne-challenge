package com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.respository;

import com.github.nenidan.ne_ne_challenge.domain.admin.domain.repository.SavedHistoryRepositoryCustom;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.request.LogSearchCond;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.logs.PaymentHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.QPayment;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SavedHistoryRepositoryImpl implements SavedHistoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public SavedHistoryRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<PaymentHistoryResponse> findPaymentHistories(LogSearchCond cond) {
        QPayment payment = QPayment.payment;

        return queryFactory
                .select(Projections.constructor(PaymentHistoryResponse.class,
                        Expressions.constant("PAYMENT"),         // type
                        payment.createdAt,                       // createdAt
                        payment.id,                              // paymentId
                        payment.userId,                          // userId
                        payment.amount,                          // amount
                        payment.status.stringValue(),            // status
                        payment.requestedAt,                     // requestedAt
                        payment.canceledAt,                      // canceledAt
                        payment.failedAt                         // failedAt
                ))
                .from(payment)
                .where(
                        cond.getUserId() != null ? payment.userId.eq(cond.getUserId()) : null,
                        cond.getCursor() != null ? payment.createdAt.lt(cond.getCursor()) : null
                )
                .orderBy(payment.createdAt.desc())
                .limit(cond.getSize() + 1L)
                .fetch();
    }
}
