package com.github.nenidan.ne_ne_challenge.domain.shop.review.applicaion;

import org.springframework.stereotype.Component;

import com.github.nenidan.ne_ne_challenge.domain.shop.review.applicaion.service.ReviewService;
import com.github.nenidan.ne_ne_challenge.global.client.product.ProductRestClient;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.applicaion.dto.ReviewCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.applicaion.dto.ReviewResult;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;
import com.github.nenidan.ne_ne_challenge.global.client.product.dto.ProductResponse;
import com.github.nenidan.ne_ne_challenge.global.client.user.UserClient;
import com.github.nenidan.ne_ne_challenge.global.client.user.dto.UserResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewFacade {

    private final ReviewService reviewService;
    private final UserClient userClient;
    private final ProductRestClient productRestClient;

    public ReviewResult createReview(ReviewCommand reviewCommand){
        // 유저 검증 및 유저 정보 API 호출
        UserResponse user = userClient.getUserById(reviewCommand.getUserId().getValue());
        // 상품 검증 및 상품 정보 API 호출
        ProductResponse product = productRestClient.getProduct(reviewCommand.getProductId());
        return reviewService.createReview(new UserId(user.getId()), new ProductId(product.getId()), reviewCommand.getRating());
    }

    public ReviewResult updateReview(ReviewCommand reviewCommand) {
        // 유저 검증 및 유저 정보 API 호출
        UserResponse user = userClient.getUserById(reviewCommand.getUserId().getValue());
        // 상품 검증 및 상품 정보 API 호출
        ProductResponse product = productRestClient.getProduct(reviewCommand.getProductId());
        return reviewService.updateReview(new UserId(user.getId()), new ProductId(product.getId()), reviewCommand.getRating());
    }

    public void deleteAllReviewBy(ProductId productId) {
        // 상품 검증 및 상품 정보 API 호출
        ProductResponse product = productRestClient.getProduct(productId);
        reviewService.deleteAllReviewByProductId(new ProductId(product.getId()));
    }
}
