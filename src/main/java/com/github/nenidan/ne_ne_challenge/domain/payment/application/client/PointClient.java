package com.github.nenidan.ne_ne_challenge.domain.payment.application.client;

import com.github.nenidan.ne_ne_challenge.domain.payment.application.dto.request.PointClientCommand;

public interface PointClient {

    void chargePoint(PointClientCommand pointClientCommand);
}