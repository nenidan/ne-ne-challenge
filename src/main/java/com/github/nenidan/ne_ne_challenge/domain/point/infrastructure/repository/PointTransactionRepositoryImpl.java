package com.github.nenidan.ne_ne_challenge.domain.point.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.point.domain.PointTransaction;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.repository.PointTransactionRepository;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.type.PointReason;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PointTransactionRepositoryImpl implements PointTransactionRepository {

    private final JpaPointTransactionRepository jpaPointTransactionRepository;

    @Override
    public PointTransaction save(PointTransaction pointTransaction) {
        return jpaPointTransactionRepository.save(pointTransaction);
    }

    @Override
    public List<PointTransaction> searchMyPointHistory(Long pointWalletId, Long cursor, PointReason reason,
        LocalDateTime startDate, LocalDateTime endDate, int limit) {
        return jpaPointTransactionRepository.searchMyPointHistory(pointWalletId, cursor, reason, startDate, endDate, limit);
    }
}
