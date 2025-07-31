package com.github.nenidan.ne_ne_challenge.domain.shop.review.infrastructure;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.shop.review.domain.Review;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.domain.ReviewRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.domain.exception.ReviewErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.domain.exception.ReviewException;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.infrastructure.entity.ReviewEntity;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.infrastructure.mapper.ReviewMapper;
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

    @Override
    public Review findById(UserId userId, ProductId productId) {
        ReviewEntity reviewEntity = reviewJpaRepository.findByUserIdAndProductId(userId.getValue(), productId.getValue())
            .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));
        return ReviewMapper.toDomain(reviewEntity);
    }

    @Override
    public Review update(Review review) {
        ReviewEntity reviewEntity = reviewJpaRepository.findByUserIdAndProductId(review.getUserId().getValue(), review.getProductId().getValue())
            .orElseThrow(() -> new ReviewException(ReviewErrorCode.REVIEW_NOT_FOUND));
        reviewEntity.update(review.getRating());
        return ReviewMapper.toDomain(reviewEntity);
    }

    @Override
    public void deleteAllReviewByProductId(ProductId productId) {
        reviewJpaRepository.deleteAllByProductId(productId.getValue(),
            LocalDateTime.now());
    }
}
