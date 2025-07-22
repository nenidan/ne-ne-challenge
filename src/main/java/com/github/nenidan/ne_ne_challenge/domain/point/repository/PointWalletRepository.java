package com.github.nenidan.ne_ne_challenge.domain.point.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.nenidan.ne_ne_challenge.domain.point.entity.PointWallet;

public interface PointWalletRepository extends JpaRepository<PointWallet, Long> {
}
