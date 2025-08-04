package com.github.nenidan.ne_ne_challenge.domain.shop.review.domain.repository;

import com.github.nenidan.ne_ne_challenge.domain.shop.review.domain.model.Review;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;

public interface ReviewRepository {
    Review save(Review review);
    boolean exists(UserId userId, ProductId productId);
    Review findById(UserId userId, ProductId productId);
    void deleteAllReviewByProductId(ProductId productId);
}
