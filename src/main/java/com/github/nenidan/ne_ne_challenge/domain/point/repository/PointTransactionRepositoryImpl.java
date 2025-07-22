package com.github.nenidan.ne_ne_challenge.domain.point.repository;

import java.time.LocalDateTime;
import java.util.List;

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
    public List<PointTransaction> searchMyPointHistory(Long pointWalletId, Long cursor, int limit, PointReason reason, LocalDateTime startDate, LocalDateTime endDate) {
        StringBuilder sb = new StringBuilder("SELECT p FROM PointTransaction p WHERE p.pointWallet.id = :pointWalletId");

        if (cursor != null) {
            sb.append(" AND p.id <= :cursor");
        }

        if (reason != null) {
            sb.append(" AND p.reason = :reason");
        }

        if (startDate != null) {
            sb.append(" AND p.createdAt >= :startDate");
        }

        if (endDate != null) {
            sb.append(" AND p.createdAt <= :endDate");
        }

        sb.append(" ORDER BY p.id DESC");

        TypedQuery<PointTransaction> query = em.createQuery(sb.toString(), PointTransaction.class)
            .setParameter("pointWalletId", pointWalletId)
            .setMaxResults(limit);

        if (cursor != null) {
            query.setParameter("cursor", cursor);
        }

        if (reason != null) {
            query.setParameter("reason", reason);
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
