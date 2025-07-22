package com.github.nenidan.ne_ne_challenge.domain.payment.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.payment.entity.Payment;
import com.github.nenidan.ne_ne_challenge.domain.payment.type.PaymentMethod;
import com.github.nenidan.ne_ne_challenge.domain.payment.type.PaymentStatus;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Payment> searchPayments(
        Long userId,
        Long cursor,
        int limit,
        PaymentMethod method,
        PaymentStatus status,
        LocalDateTime startDate,
        LocalDateTime endDate) {

        StringBuilder sb = new StringBuilder("SELECT p FROM Payment p where p.user.id = :userId");

        if (cursor != null) {
            sb.append(" AND p.id < :cursor");
        }

        if (method != null) {
            sb.append(" AND p.method = :method");
        }

        if (status != null) {
            sb.append(" AND p.status = :status");
        }

        if (startDate != null) {
            sb.append(" AND p.updatedAt >= :startDate");
        }

        if (endDate != null) {
            sb.append(" AND p.updatedAt <= :endDate");
        }

        sb.append(" ORDER BY p.id DESC");

        TypedQuery<Payment> query = em.createQuery(sb.toString(), Payment.class)
            .setParameter("userId", userId)
            .setMaxResults(limit);

        if (cursor != null) {
            query.setParameter("cursor", cursor);
        }

        if (method != null) {
            query.setParameter("method", method);
        }

        if (status != null) {
            query.setParameter("status", status);
        }

        if (startDate != null) {
            query.setParameter("startDate", startDate);
        }

        if (endDate != null) {
            query.setParameter("endDate", endDate);
        }

        return query.getResultList();
    }
}
