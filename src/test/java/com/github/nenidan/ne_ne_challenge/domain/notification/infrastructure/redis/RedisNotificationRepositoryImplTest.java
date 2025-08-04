package com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.redis;

import static org.assertj.core.api.AssertionsForInterfaceTypes.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nenidan.ne_ne_challenge.domain.notification.application.dto.request.RetryNotificationRequest;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.log.NotificationLog;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.log.NotificationStatus;
import com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.fcm.Platform;

@DataRedisTest
@Import(RedisNotificationRepositoryImpl.class)
class RedisNotificationRepositoryImplTest {
	@Autowired
	RedisNotificationRepositoryImpl redisNotificationRepository;

	@Test
	@DisplayName("push한 알림은 정확히 5초후에 꺼내져야 한다")
	void push() throws InterruptedException {
		NotificationLog log = new NotificationLog(1L, "title", "content", Platform.WEB, NotificationStatus.WAITING);
		redisNotificationRepository.push(log,5);

		List<RetryNotificationRequest> before = redisNotificationRepository.getNotifications(System.currentTimeMillis());
		assertThat(before).isEmpty(); // 너무 이르니까 아무것도 안 나와야 하고

		Thread.sleep(5500); // 5.5초 대기

		List<RetryNotificationRequest> after = redisNotificationRepository.getNotifications(System.currentTimeMillis());
		assertThat(after).hasSize(1);

		// Redis에 남은 게 없어야 함
		after = redisNotificationRepository.getNotifications(System.currentTimeMillis());
		assertThat(after.size()).isEqualTo(0);
	}

	@Test
	@DisplayName("조회 성능 측정")
	void bigLogTest(){
		for (int i = 0; i < 3000; i++) {
			NotificationLog log = new NotificationLog(1L, "title" + i, "content", Platform.WEB, NotificationStatus.WAITING);
			redisNotificationRepository.push(log,0);
		}
		long start = System.nanoTime();
		List<RetryNotificationRequest> after = redisNotificationRepository.getNotifications(System.currentTimeMillis());
		long end = System.nanoTime();

		double elapsedMs = (end - start) / 1_000_000.0;
		System.out.printf("\n조회 결과 = %d건, 소요 시간 = %.2f ms%n\n", after.size(), elapsedMs);
	}

	@TestConfiguration
	static class Config {
		@Bean
		public ObjectMapper objectMapper() {
			return new ObjectMapper();
		}
	}
}