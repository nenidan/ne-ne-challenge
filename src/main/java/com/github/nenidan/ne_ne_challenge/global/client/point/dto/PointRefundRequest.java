package com.github.nenidan.ne_ne_challenge.global.client.point.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PointRefundRequest {

    private List<Long> userList;
    private int amount;
}
