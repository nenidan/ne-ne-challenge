package com.github.nenidan.ne_ne_challenge.domain.point.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.github.nenidan.ne_ne_challenge.domain.point.domain.PointTransaction;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.PointWallet;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.type.PointReason;

public interface PointTransactionRepository {

    PointTransaction save(PointTransaction pointTransaction);

    List<PointTransaction> searchMyPointHistory(
        Long pointWalletId,
        Long cursor,
        PointReason reason,
        LocalDateTime startDate,
        LocalDateTime endDate,
        int limit
    );

    boolean existsByUserId(Long userId);

    PointWallet save(PointWallet pointWallet);

    Optional<PointWallet> findWalletByUserId(Long userId);

}
