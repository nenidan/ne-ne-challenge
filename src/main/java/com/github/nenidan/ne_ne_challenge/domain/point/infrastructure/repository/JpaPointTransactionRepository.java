package com.github.nenidan.ne_ne_challenge.domain.point.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.nenidan.ne_ne_challenge.domain.point.domain.model.PointTransaction;

public interface JpaPointTransactionRepository extends JpaRepository<PointTransaction, Long>{
}
