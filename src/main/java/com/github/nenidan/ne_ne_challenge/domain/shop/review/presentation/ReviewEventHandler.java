package com.github.nenidan.ne_ne_challenge.domain.shop.review.presentation;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.github.nenidan.ne_ne_challenge.domain.shop.review.applicaion.ReviewFacade;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ReviewDeleteEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewEventHandler {

    private final ReviewFacade reviewFacade;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void deleteAllReview(ReviewDeleteEvent reviewDeleteEvent) {
        reviewFacade.deleteAllReviewBy(reviewDeleteEvent.getProductId().getValue());
    }
}
