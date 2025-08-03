package com.github.nenidan.ne_ne_challenge.domain.payment.infrastructure.client.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TossCancelRequest {

    private String cancelReason;
}
