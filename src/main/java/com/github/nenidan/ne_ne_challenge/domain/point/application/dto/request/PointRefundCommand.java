package com.github.nenidan.ne_ne_challenge.domain.point.application.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class PointRefundCommand {

    private List<Long> userList;
    private int amount;
}
