package com.github.nenidan.ne_ne_challenge.domain.shop.review.applicaion.dto;

import com.github.nenidan.ne_ne_challenge.domain.shop.review.domain.model.Review;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ReviewId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewResult {
    private final ReviewId reviewId;
    private final ProductId productId;
    private final UserId userId;
    private final int rating;

    public static ReviewResult fromEntity(Review review){
        return new ReviewResult(
            review.getReviewId(),
            review.getProductId(),
            review.getUserId(),
            review.getRating()
        );
    }
}
