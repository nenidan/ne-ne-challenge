package com.github.nenidan.ne_ne_challenge.domain.shop.review.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.nenidan.ne_ne_challenge.domain.shop.review.infrastructure.entity.ReviewEntity;

public interface ReviewJpaRepository extends JpaRepository<ReviewEntity,Long> {
    boolean existsByUserIdAndProductId(Long userId, Long productId);

    Optional<ReviewEntity> findByUserIdAndProductId(Long userId, Long productId);
}
