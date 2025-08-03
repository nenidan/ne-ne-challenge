package com.github.nenidan.ne_ne_challenge.domain.shop.order.application.dto;

import com.github.nenidan.ne_ne_challenge.domain.shop.vo.UserId;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FindCursorOrderCommand {
    private final UserId userId;
    private final Long cursor;
    private final int size;
    private final String keyword;
}
