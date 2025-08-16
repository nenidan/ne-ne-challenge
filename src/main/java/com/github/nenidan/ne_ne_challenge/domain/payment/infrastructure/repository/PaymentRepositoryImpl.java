package com.github.nenidan.ne_ne_challenge.domain.payment.infrastructure.repository;

import static com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.QPayment.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.github.nenidan.ne_ne_challenge.domain.payment.domain.model.Payment;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.repository.PaymentRepository;
import com.github.nenidan.ne_ne_challenge.domain.payment.domain.type.PaymentStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PaymentRepositoryImpl implements PaymentRepository {

    private final JpaPaymentRepository jpaPaymentRepository;
    private final JPAQueryFactory factory;

    @Override
    public Payment save(Payment payment) {
        return jpaPaymentRepository.save(payment);
    }

    @Override
    public List<Payment> searchPayments(Long userId, Long cursor, String status,
        LocalDateTime startDate, LocalDateTime endDate, int limit) {

        BooleanBuilder whereClause = buildWhereCondition(userId, cursor, status, startDate, endDate);

        return factory.selectFrom(payment)
            .where(whereClause)
            .orderBy(payment.id.desc())
            .limit(limit)
            .fetch();
    }

    @Override
    public Optional<Payment> findByOrderId(String orderId) {
        Payment result = factory.selectFrom(payment)
            .where(payment.orderId.value.eq(orderId))
            .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public List<Payment> findAll() {
        return jpaPaymentRepository.findAll();
    }

    // ================ 동적 검색 조건 ================
    private BooleanBuilder buildWhereCondition(Long userId, Long cursor, String status, LocalDateTime startDate,
        LocalDateTime endDate) {

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(payment.userId.eq(userId));

        if (cursor != null) {
            builder.and(payment.id.loe(cursor));
        }

        if (StringUtils.hasText(status)) {
            builder.and(payment.status.eq(PaymentStatus.of(status)));
        }

        if (startDate != null && endDate != null) {
            builder.and(payment.updatedAt.between(startDate, endDate));
        } else if (startDate != null) {
            builder.and(payment.updatedAt.goe(startDate));
        } else if (endDate != null) {
            builder.and(payment.updatedAt.loe(endDate));
        }

        return builder;
    }
}
