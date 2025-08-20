package com.github.nenidan.ne_ne_challenge.domain.point.presentation.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class PointRefundRequest {

    private List<Long> userList;
    private int amount;
}
