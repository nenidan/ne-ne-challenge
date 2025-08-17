package com.github.nenidan.ne_ne_challenge.global.trace;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TraceContext implements Serializable {
    private String traceId;
    private Long userId;
    private String username;
    private String ip;
    private String uri;
    private String httpMethod;
    private String userAgent;
}
