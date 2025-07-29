package com.github.nenidan.ne_ne_challenge.domain.shop.review.infrastructure;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.github.nenidan.ne_ne_challenge.domain.shop.review.infrastructure.entity.ReviewEntity;

public interface ReviewJpaRepository extends JpaRepository<ReviewEntity,Long> {
    boolean existsByUserIdAndProductId(Long userId, Long productId);

    Optional<ReviewEntity> findByUserIdAndProductId(Long userId, Long productId);

    @Modifying
    @Query("UPDATE ReviewEntity re SET re.deletedAt = :deletedAt WHERE re.productId = :productId")
    void deleteAllByProductId(Long productId, LocalDateTime deletedAt);
}
