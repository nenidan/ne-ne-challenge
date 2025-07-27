package com.github.nenidan.ne_ne_challenge.domain.point.domain.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.point.domain.PointTransaction;
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
}
