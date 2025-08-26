package com.github.nenidan.ne_ne_challenge.domain.point.infrastructure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.nenidan.ne_ne_challenge.domain.point.domain.model.Point;

public interface JpaPointRepository extends JpaRepository<Point, Long> {
    Optional<Point> findBySourceOrderId(String sourceOrderId);
}
