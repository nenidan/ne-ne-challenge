package com.github.nenidan.ne_ne_challenge.domain.shop.review.domain.model;

import com.github.nenidan.ne_ne_challenge.domain.shop.review.domain.exception.ReviewErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.domain.exception.ReviewException;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ReviewId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;

import lombok.Getter;

@Getter
public class Review {
    private final ProductId productId;
    private final UserId userId;
    private ReviewId reviewId;
    private int rating;

    private Review(ProductId productId, UserId userId, int rating) {
        this.productId = productId;
        this.userId = userId;
        updateRating(rating);
    }

    private Review(ReviewId reviewId, ProductId productId, UserId userId, int rating) {
        this.reviewId = reviewId;
        this.productId = productId;
        this.userId = userId;
        this.rating = rating;
    }

    public static Review create(ProductId productId, UserId userId, int rating) {
        return new Review(productId, userId, rating);
    }

    public static Review reload(ReviewId reviewId, ProductId productId, UserId userId, int rating) {
        return new Review(reviewId, productId, userId, rating);
    }

    public void updateRating(int rating) {
        if (rating < 0 || rating > 5) {
            throw new ReviewException(ReviewErrorCode.REVIEW_VALUE_INVALID);
        }
        this.rating = rating;
    }
}
