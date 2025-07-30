package com.github.nenidan.ne_ne_challenge.domain.shop.review.applicaion;

import org.springframework.stereotype.Component;

import com.github.nenidan.ne_ne_challenge.domain.shop.global.ProductRestClient;
import com.github.nenidan.ne_ne_challenge.domain.shop.global.UserRestClient;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.presentation.dto.ProductResponse;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.applicaion.dto.ReviewCommand;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.applicaion.dto.ReviewResult;
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

    public ReviewResult createReview(ReviewCommand reviewCommand){
        UserResponse user = userRestClient.getUser(reviewCommand.getUserId().getValue());
        ProductResponse product = productRestClient.getProduct(reviewCommand.getProductId().getValue());
        return reviewService.createReview(new UserId(user.getId()), new ProductId(product.getId()), reviewCommand.getRating());
    }

    public ReviewResult updateReview(ReviewCommand reviewCommand) {
        UserResponse user = userRestClient.getUser(reviewCommand.getUserId().getValue());
        ProductResponse product = productRestClient.getProduct(reviewCommand.getProductId().getValue());
        return reviewService.updateReview(new UserId(user.getId()), new ProductId(product.getId()), reviewCommand.getRating());
    }

    public void deleteAllReviewBy(Long productId) {
        ProductResponse product = productRestClient.getProduct(productId);
        reviewService.deleteAllReviewByProductId(new ProductId(product.getId()));
    }
}
