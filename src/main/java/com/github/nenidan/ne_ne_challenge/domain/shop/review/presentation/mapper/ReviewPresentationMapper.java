package com.github.nenidan.ne_ne_challenge.domain.shop.review.presentation.mapper;

import com.github.nenidan.ne_ne_challenge.domain.shop.review.applicaion.dto.ReviewCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.applicaion.dto.ReviewResult;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.presentation.dto.ReviewRequest;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.presentation.dto.ReviewResponse;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;

public class ReviewPresentationMapper {

    public static ReviewCommand toReviewCommand(Long userId, Long productId, ReviewRequest reviewRequest) {
        return new ReviewCommand(
            new UserId(userId),
            new ProductId(productId),
            reviewRequest.getRating()
        );
    }

    public static ReviewResponse fromReviewResult (ReviewResult reviewResult) {
        return new ReviewResponse(
            reviewResult.getReviewId().getValue(),
            reviewResult.getProductId().getValue(),
            reviewResult.getUserId().getValue(),
            reviewResult.getRating()
        );
    }
}
