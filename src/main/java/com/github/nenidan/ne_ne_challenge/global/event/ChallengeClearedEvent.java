package com.github.nenidan.ne_ne_challenge.global.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChallengeClearedEvent {
	private final Long challengeId;
	private final Long userId;
}
// ApplicationEventPublisher eventPublisher;
// eventPublisher.publishEvent(new ChallengeClearedEvent(userId, challengeId));