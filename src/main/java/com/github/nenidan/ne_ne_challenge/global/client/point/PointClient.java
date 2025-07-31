package com.github.nenidan.ne_ne_challenge.global.client.point;


import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.PointClientCommand;

public interface PointClient {

    void createPointWallet(Long userId);

    void chargePoint(PointClientCommand pointClientCommand);
}
