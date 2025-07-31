package com.github.nenidan.ne_ne_challenge.domain.point.domain.repository;

import java.util.Optional;

import com.github.nenidan.ne_ne_challenge.domain.point.domain.PointWallet;

public interface PointWalletRepository {

    boolean existsByUserId(Long userId);

    PointWallet save(PointWallet pointWallet);

    Optional<PointWallet> findWalletByUserId(Long userId);

}
