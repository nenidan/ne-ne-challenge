package com.github.nenidan.ne_ne_challenge.domain.point.repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.point.entity.PointTransaction;
import com.github.nenidan.ne_ne_challenge.domain.point.type.PointReason;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class PointTransactionRepositoryImpl implements PointTransactionRepositoryCustom{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<PointTransaction> searchMyPointHistory(
        Long pointWalletId,
        Long cursor,
        int limit,
        PointReason reason,
        LocalDateTime startDate,
        LocalDateTime endDate) {

        StringBuilder sb = new StringBuilder("SELECT p FROM PointTransaction p WHERE p.pointWallet.id = :pointWalletId");
        Map<String, Object> params = new HashMap<>();
        params.put("pointWalletId", pointWalletId);

        if (cursor != null) {
            sb.append(" AND p.id <= :cursor");
            params.put("cursor", cursor);
        }

        if (reason != null) {
            sb.append(" AND p.reason = :reason");
            params.put("reason", reason);
        }

        if (startDate != null) {
            sb.append(" AND p.createdAt >= :startDate");
            params.put("startDate", startDate);
        }

        if (endDate != null) {
            sb.append(" AND p.createdAt <= :endDate");
            params.put("endDate", endDate);
        }

        sb.append(" ORDER BY p.id DESC");

        TypedQuery<PointTransaction> query = em.createQuery(sb.toString(), PointTransaction.class)
            .setMaxResults(limit);

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        return query.getResultList();
    }
}
