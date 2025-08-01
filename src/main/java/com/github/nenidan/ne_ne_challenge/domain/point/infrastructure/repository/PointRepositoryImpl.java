package com.github.nenidan.ne_ne_challenge.domain.point.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.point.domain.PointTransaction;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.PointWallet;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.repository.PointRepository;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.type.PointReason;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PointRepositoryImpl implements PointRepository {

    private final JpaPointWalletRepository jpaPointWalletRepository;
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

    @Override
    public boolean existsByUserId(Long userId) {
        return jpaPointWalletRepository.existsByUserId(userId);
    }

    @Override
    public PointWallet save(PointWallet pointWallet) {
        return jpaPointWalletRepository.save(pointWallet);
    }

    @Override
    public Optional<PointWallet> findWalletByUserId(Long userId) {
        return jpaPointWalletRepository.findByUserId(userId);
    }
}
