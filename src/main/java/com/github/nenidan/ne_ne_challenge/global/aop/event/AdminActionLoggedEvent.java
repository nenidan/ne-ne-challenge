package com.github.nenidan.ne_ne_challenge.global.aop.event;

import com.github.nenidan.ne_ne_challenge.domain.admin.domain.type.DomainType;

public record AdminActionLoggedEvent(DomainType type, Long targetId, String method, String params, String result, boolean success) {}
