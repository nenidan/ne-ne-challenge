package com.github.nenidan.ne_ne_challenge.domain.shop.review.domain;

import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.exception.ShopException;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ReviewId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;

import lombok.Getter;

@Getter
public class Review {
    private ReviewId reviewId;
    private final ProductId productId;
    private final UserId userId;
    private int rating;

    private Review(ProductId productId, UserId userId, int rating) {
        this.productId = productId;
        this.userId = userId;
        updateRating(rating);
    }

    public Review(ReviewId reviewId, ProductId productId, UserId userId, int rating) {
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
            throw new ShopException(ShopErrorCode.REVIEW_VALUE_INVALID);
        }
        this.rating = rating;
    }
}
