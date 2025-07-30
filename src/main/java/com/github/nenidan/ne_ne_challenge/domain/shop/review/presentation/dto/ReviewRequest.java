package com.github.nenidan.ne_ne_challenge.domain.shop.review.presentation.dto;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewRequest {

    @Range(min = 1, max = 5, message = "리뷰 점수는 1 이상 5이하여야 합니다.")
    private final int rating;
}
