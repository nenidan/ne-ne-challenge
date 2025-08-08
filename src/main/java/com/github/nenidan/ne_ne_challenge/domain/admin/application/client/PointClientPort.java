package com.github.nenidan.ne_ne_challenge.domain.admin.application.client;

import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.out.PaymentDto;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.out.PointDto;

import java.util.List;

public interface PointClientPort {
    List<PointDto> getAllPoint();
}
