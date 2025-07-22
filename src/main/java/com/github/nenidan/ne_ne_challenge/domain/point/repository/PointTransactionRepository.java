package com.github.nenidan.ne_ne_challenge.domain.point.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.nenidan.ne_ne_challenge.domain.point.entity.PointTransaction;

public interface PointTransactionRepository extends JpaRepository<PointTransaction, Long>, PointTransactionRepositoryCustom {
}
