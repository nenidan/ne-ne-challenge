package com.github.nenidan.ne_ne_challenge.domain.payment.repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.payment.entity.Payment;
import com.github.nenidan.ne_ne_challenge.domain.payment.type.PaymentMethod;
import com.github.nenidan.ne_ne_challenge.domain.payment.type.PaymentStatus;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
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

        StringBuilder sb = new StringBuilder("SELECT p FROM Payment p WHERE p.user.id = :userId");
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);

        if (cursor != null) {
            sb.append(" AND p.id <= :cursor");
            params.put("cursor", cursor);
        }

        if (method != null) {
            sb.append(" AND p.method = :method");
            params.put("method", method);
        }

        if (status != null) {
            sb.append(" AND p.status = :status");
            params.put("status", status);
        }

        if (startDate != null) {
            sb.append(" AND p.updatedAt >= :startDate");
            params.put("startDate", startDate);
        }

        if (endDate != null) {
            sb.append(" AND p.updatedAt <= :endDate");
            params.put("endDate", endDate);
        }

        sb.append(" ORDER BY p.id DESC");

        TypedQuery<Payment> query = em.createQuery(sb.toString(), Payment.class)
            .setMaxResults(limit);

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        return query.getResultList();
    }
}
