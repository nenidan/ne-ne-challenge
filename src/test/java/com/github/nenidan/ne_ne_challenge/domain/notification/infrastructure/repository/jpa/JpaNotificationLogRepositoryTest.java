package com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.repository.jpa;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.log.NotificationLog;
import com.github.nenidan.ne_ne_challenge.domain.notification.domain.entity.log.NotificationStatus;
import com.github.nenidan.ne_ne_challenge.domain.notification.infrastructure.fcm.Platform;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableJpaAuditing
class JpaNotificationLogRepositoryTest {

	@Autowired
	private JpaNotificationLogRepository notificationLogRepository;

	@Test
	@DisplayName("조회 성능 측정")
	void bigLogTest() {
		// given
		LocalDateTime now = LocalDateTime.now();
		for (int i = 0; i < 500; i++) {
			NotificationLog log = new NotificationLog(1L, "title" + i, "content", Platform.WEB, NotificationStatus.WAITING);

			notificationLogRepository.save(log);
		}

		// when
		long start = System.nanoTime();

		List<NotificationLog> logs = notificationLogRepository.findByStatusAndCreatedAtAfter(
			NotificationStatus.WAITING,
			now.minusMinutes(10)
		);

		long end = System.nanoTime();

		// then
		double elapsedMs = (end - start) / 1_000_000.0;
		System.out.printf("\n조회 결과 = %d건, 소요 시간 = %.2f ms%n\n", logs.size(), elapsedMs);
	}
}