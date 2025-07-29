package com.github.nenidan.ne_ne_challenge.domain.shop.review.applicaion;

import org.springframework.stereotype.Component;

import com.github.nenidan.ne_ne_challenge.domain.shop.global.ProductRestClient;
import com.github.nenidan.ne_ne_challenge.domain.shop.global.UserRestClient;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.ProductResponse;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.applicaion.dto.ReviewResponse;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;
import com.github.nenidan.ne_ne_challenge.domain.user.dto.response.UserResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewFacade {

    private final ReviewService reviewService;
    private final UserRestClient userRestClient;
    private final ProductRestClient productRestClient;

    public ReviewResponse createReview(Long userId, Long productId, int rating){
        UserResponse user = userRestClient.getUser(userId);
        ProductResponse product = productRestClient.getProduct(productId);
        return reviewService.createReview(new UserId(user.getId()), new ProductId(product.getId()), rating);
    }

    public ReviewResponse updateReview(Long userId, Long productId, int  rating) {
        ProductResponse product = productRestClient.getProduct(productId);
        return reviewService.updateReview(new UserId(userId), new ProductId(product.getId()), rating);
    }

    public void deleteAllReviewBy(Long productId) {
        ProductResponse product = productRestClient.getProduct(productId);
        reviewService.deleteAllReviewByProductId(new ProductId(product.getId()));
    }
}
