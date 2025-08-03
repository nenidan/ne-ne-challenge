package com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.respository;

import com.github.nenidan.ne_ne_challenge.domain.admin.domain.repository.SavedHistoryRepositoryCustom;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.request.LogSearchCond;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.response.logs.PaymentHistoryResponse;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.QPayment;
import com.querydsl.core.types.Projections;
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
                        payment.id,
                        payment.user.id,
                        payment.amount,
                        payment.status,
                        payment.method,
                        payment.requestedAt,
                        payment.confirmedAt,
                        payment.failedAt,
                        payment.createdAt
                ))
                .from(payment)
                .where(
                        cond.getId() != null ? payment.user.id.eq(cond.getId()) : null,
                        cond.getCursor() != null ? payment.createdAt.lt(cond.getCursor()) : null
                )
                .orderBy(payment.createdAt.desc())
                .limit(cond.getSize() + 1L)
                .fetch();
    }
}
