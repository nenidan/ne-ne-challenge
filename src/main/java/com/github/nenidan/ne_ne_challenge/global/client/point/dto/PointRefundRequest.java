package com.github.nenidan.ne_ne_challenge.global.client.point.dto;

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
