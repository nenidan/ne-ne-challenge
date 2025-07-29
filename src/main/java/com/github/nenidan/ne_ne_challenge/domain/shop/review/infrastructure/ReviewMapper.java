package com.github.nenidan.ne_ne_challenge.domain.shop.review.infrastructure;

import com.github.nenidan.ne_ne_challenge.domain.shop.review.domain.Review;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.infrastructure.entity.ReviewEntity;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ReviewId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;

public class ReviewMapper {

    public static Review toDomain (ReviewEntity reviewEntity){
        return Review.reload(
            new ReviewId(reviewEntity.getId()),
            new ProductId(reviewEntity.getProductId()),
            new UserId(reviewEntity.getUserId()),
            reviewEntity.getRating()
        );
    }

    public static ReviewEntity toEntity (Review review){
        return new ReviewEntity(
            review.getProductId().getValue(),
            review.getUserId().getValue(),
            review.getRating()
        );
    }
}
