package com.github.nenidan.ne_ne_challenge.domain.shop.review.infrastructure;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopException;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.domain.Review;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.domain.ReviewRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.infrastructure.entity.ReviewEntity;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewJpaRepository reviewJpaRepository;

    @Override
    public Review save(Review review) {
        ReviewEntity entity = ReviewMapper.toEntity(review);
        ReviewEntity save = reviewJpaRepository.save(entity);
        return ReviewMapper.toDomain(save);
    }

    @Override
    public boolean exists(UserId userId, ProductId productId) {
        return reviewJpaRepository.existsByUserIdAndProductId(userId.getValue(), productId.getValue());
    }
}
