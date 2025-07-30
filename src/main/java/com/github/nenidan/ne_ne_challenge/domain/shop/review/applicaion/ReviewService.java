package com.github.nenidan.ne_ne_challenge.domain.shop.review.applicaion;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopException;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.applicaion.dto.ReviewResult;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.domain.Review;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.domain.ReviewRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public ReviewResult createReview(UserId userId, ProductId productId, int rating){
        if (reviewRepository.exists(userId, productId)) {
            throw new ShopException(ShopErrorCode.REVIEW_ALREADY_EXISTS);
        }
        Review review = Review.create(
            productId,
            userId,
            rating
        );
        Review saveReview = reviewRepository.save(review);
        return ReviewResult.fromEntity(saveReview);
    }

    @Transactional
    public ReviewResult updateReview(UserId userId, ProductId productId, int rating){
        Review review = reviewRepository.findById(userId, productId);
        review.updateRating(rating);
        Review update = reviewRepository.update(review);
        return ReviewResult.fromEntity(update);
    }

    @Transactional
    public void deleteAllReviewByProductId(ProductId productId){
        reviewRepository.deleteAllReviewByProductId(productId);
    }
}
