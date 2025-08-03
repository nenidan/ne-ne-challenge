package com.github.nenidan.ne_ne_challenge.domain.shop.review.applicaion.dto;


import com.github.nenidan.ne_ne_challenge.domain.shop.vo.ProductId;
import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewCommand {

    private final UserId userId;
    private final ProductId productId;
    private final int rating;
}
