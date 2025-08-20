package com.github.nenidan.ne_ne_challenge.domain.payment.infrastructure.client.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TossCancelRequest {

    private String cancelReason;
}
