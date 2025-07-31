package com.github.nenidan.ne_ne_challenge.domain.point.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.nenidan.ne_ne_challenge.domain.point.domain.PointWallet;

public interface JpaPointWalletRepository extends JpaRepository<PointWallet, Long> {
    Optional<PointWallet> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
