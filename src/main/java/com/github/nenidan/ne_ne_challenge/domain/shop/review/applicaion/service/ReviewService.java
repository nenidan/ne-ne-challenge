package com.github.nenidan.ne_ne_challenge.domain.shop.review.applicaion.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.nenidan.ne_ne_challenge.domain.shop.review.applicaion.dto.ReviewResult;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.domain.exception.ReviewErrorCode;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.domain.exception.ReviewException;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.domain.model.Review;
import com.github.nenidan.ne_ne_challenge.domain.shop.review.domain.repository.ReviewRepository;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    /**
     * 새로운 리뷰를 작성합니다.
     * <p>
     * 동일한 사용자(userId)가 같은 상품(productId)에 이미 리뷰를 작성한 경우 예외를 발생시킵니다.
     *
     * @param userId    리뷰 작성자 ID
     * @param productId 리뷰 대상 상품 ID
     * @param rating    평점
     * @return 생성된 리뷰 결과 DTO
     * @throws ReviewException 이미 리뷰가 존재할 경우 {@link ReviewErrorCode#REVIEW_ALREADY_EXISTS} 예외 발생
     * @author kimyongjun0129
     */
    @Transactional
    public ReviewResult createReview(UserId userId, ProductId productId, int rating){
        if (reviewRepository.exists(userId, productId)) {
            throw new ReviewException(ReviewErrorCode.REVIEW_ALREADY_EXISTS);
        }
        Review review = Review.create(
            productId,
            userId,
            rating
        );
        Review saveReview = reviewRepository.save(review);
        return ReviewResult.fromEntity(saveReview);
    }

    /**
     * 기존 리뷰의 평점을 수정합니다.
     *
     * @param userId    리뷰 작성자 ID
     * @param productId 리뷰 대상 상품 ID
     * @param rating    수정할 평점
     * @return 수정된 리뷰 결과 DTO
     * @author kimyongjun0129
     */
    @Transactional
    public ReviewResult updateReview(UserId userId, ProductId productId, int rating){
        Review review = reviewRepository.findById(userId, productId);
        review.updateRating(rating);
        Review update = reviewRepository.save(review);
        return ReviewResult.fromEntity(update);
    }

    /**
     * 특정 상품에 대한 모든 리뷰를 삭제합니다.
     *
     * @param productId 리뷰를 삭제할 상품 ID
     * @author kimyongjun0129
     */
    @Transactional
    public void deleteAllReviewByProductId(ProductId productId){
        reviewRepository.deleteAllReviewByProductId(productId);
    }
}
