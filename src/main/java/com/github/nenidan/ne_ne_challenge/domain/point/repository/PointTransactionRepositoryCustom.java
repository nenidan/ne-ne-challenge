package com.github.nenidan.ne_ne_challenge.domain.point.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.point.entity.PointTransaction;
import com.github.nenidan.ne_ne_challenge.domain.point.type.PointReason;

public interface PointTransactionRepositoryCustom {
    List<PointTransaction> searchMyPointHistory(Long pointWalletId, Long cursor, int limit, PointReason reason, LocalDateTime startDate, LocalDateTime endDate);
}
