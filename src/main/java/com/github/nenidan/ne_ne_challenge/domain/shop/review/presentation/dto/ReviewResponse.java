package com.github.nenidan.ne_ne_challenge.domain.shop.review.presentation.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewResponse {
    private final Long reviewId;
    private final Long productId;
    private final Long userId;
    private final int rating;
}
