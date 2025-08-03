package com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nenidan.ne_ne_challenge.domain.notification.application.dto.request.RetryNotificationRequest;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.log.NotificationLog;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Repository
@RequiredArgsConstructor
public class RedisNotificationRepositoryImpl implements RedisNotificationRepository {
	private static final String RETRY_KEY = "fcm:retry:zset";
	private final RedisTemplate<String, String> redisTemplate;
	private final ObjectMapper objectMapper;

	@Override
	public void push(NotificationLog log, int delaySeconds) {
		try {
			RetryNotificationRequest retryDto = RetryNotificationRequest.of(log);
			String json = objectMapper.writeValueAsString(retryDto);

			long retryAt = System.currentTimeMillis() + delaySeconds * 1000L;

			redisTemplate.opsForZSet().add(RETRY_KEY, json, retryAt);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<RetryNotificationRequest> getNotifications(long now) {
		Set<String> raw = redisTemplate.opsForZSet().rangeByScore(RETRY_KEY, 0, now);
		List<RetryNotificationRequest> list = new ArrayList<>();
		for (String json : Objects.requireNonNull(raw)) {
			try {
				list.add(objectMapper.readValue(json, RetryNotificationRequest.class));
				redisTemplate.opsForZSet().remove(RETRY_KEY, json); // 읽은 건 제거
			} catch (JsonProcessingException e) {
				log.error("ZSet 역직렬화 실패", e);
			}
		}
		return list;
	}
}
