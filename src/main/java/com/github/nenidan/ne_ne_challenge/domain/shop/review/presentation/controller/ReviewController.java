package com.github.nenidan.ne_ne_challenge.domain.shop.review.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.nenidan.ne_ne_challenge.domain.shop.review.applicaion.ReviewFacade;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.applicaion.dto.ReviewCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.presentation.dto.ReviewRequest;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.presentation.dto.ReviewResponse;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.presentation.mapper.ReviewPresentationMapper;
import com.github.nenidan.ne_ne_challenge.global.dto.ApiResponse;
import com.github.nenidan.ne_ne_challenge.global.security.auth.Auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewFacade reviewFacade;

    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<ApiResponse<ReviewResponse>> createReview(
        @AuthenticationPrincipal Auth auth,
        @PathVariable Long productId,
        @RequestBody @Valid ReviewRequest reviewRequest
    ){
        ReviewCommand reviewCommand = ReviewPresentationMapper.toReviewCommand(auth.getId(), productId, reviewRequest);
        ReviewResponse reviewResponse = ReviewPresentationMapper.fromReviewResult(reviewFacade.createReview(reviewCommand));
        return ApiResponse.success(HttpStatus.CREATED, "평점이 생성되었습니다.", reviewResponse);
    }

    @PatchMapping("/products/{productId}/reviews")
    public ResponseEntity<ApiResponse<ReviewResponse>> updateReview(
        @AuthenticationPrincipal Auth auth,
        @PathVariable Long productId,
        @RequestBody @Valid ReviewRequest reviewRequest
    ) {
        ReviewCommand reviewCommand = ReviewPresentationMapper.toReviewCommand(auth.getId(), productId, reviewRequest);
        ReviewResponse reviewResponse = ReviewPresentationMapper.fromReviewResult(reviewFacade.updateReview(reviewCommand));
        return ApiResponse.success(HttpStatus.OK, "평점이 수정되었습니다.", reviewResponse);
    }
}
