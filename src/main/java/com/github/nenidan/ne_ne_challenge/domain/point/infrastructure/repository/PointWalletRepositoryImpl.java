package com.github.nenidan.ne_ne_challenge.domain.point.infrastructure.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.point.domain.PointWallet;
import com.github.nenidan.ne_ne_challenge.domain.point.domain.repository.PointWalletRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PointWalletRepositoryImpl implements PointWalletRepository{

    private final JpaPointWalletRepository jpaPointWalletRepository;

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
