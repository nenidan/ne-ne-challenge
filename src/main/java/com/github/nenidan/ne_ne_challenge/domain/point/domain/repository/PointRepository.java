package com.github.nenidan.ne_ne_challenge.domain.point.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.github.nenidan.ne_ne_challenge.domain.point.domain.model.Point;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.model.PointTransaction;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.model.PointWallet;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.type.PointReason;

public interface PointRepository {

    PointTransaction save(PointTransaction pointTransaction);

    PointWallet save(PointWallet pointWallet);

    Point save(Point point);

    List<PointTransaction> searchMyPointHistory(
        Long pointWalletId,
        Long cursor,
        PointReason reason,
        LocalDateTime startDate,
        LocalDateTime endDate,
        int limit
    );

    boolean existsByUserId(Long userId);


    Optional<PointWallet> findWalletByUserId(Long userId);

    Optional<Point> findBySourceOrderId(String orderId);

    List<Point> findUsablePointsByWalletId(Long walletId);

    List<PointTransaction> findAll();
}
