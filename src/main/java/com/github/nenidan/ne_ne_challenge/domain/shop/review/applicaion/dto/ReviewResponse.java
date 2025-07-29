package com.github.nenidan.ne_ne_challenge.domain.shop.review.applicaion.dto;

import com.github.nenidan.ne_ne_challenge.domain.shop.review.domain.Review;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewResponse {
    private final Long reviewId;
    private final Long productId;
    private final Long userId;
    private final int rating;

    public static ReviewResponse fromEntity(Review review){
        return new ReviewResponse(
            review.getReviewId().getValue(),
            review.getProductId().getValue(),
            review.getUserId().getValue(),
            review.getRating()
        );
    }
}
