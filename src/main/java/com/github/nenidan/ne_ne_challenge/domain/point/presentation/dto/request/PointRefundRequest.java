package com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PointRefundRequest {

    private List<Long> userList;
    private int amount;
}
