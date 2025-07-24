package com.github.nenidan.ne_ne_challenge.notification.infra.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.github.nenidan.ne_ne_challenge.notification.domain.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	@Query(value = """
		SELECT *
		FROM notification
		WHERE (:cursorId IS NULL OR id > :cursorId)
		  AND receiver_id = :userId
		  AND is_read = :isRead
		  AND (:type = 'ALL' OR type = :type)
		ORDER BY id
		LIMIT :size
	""", nativeQuery = true)
	List<Notification> findAllByUserIdAndIsReadWithType(
		@Param("userId") Long userId,
		@Param("isRead") boolean isRead,
		@Param("type") String type, // ← Enum.name()으로 넘김
		@Param("cursorId") Long cursorId,
		@Param("size") int size);

	@Query(value = """
		SELECT *
		FROM notification
		WHERE (:cursorId IS NULL OR id > :cursorId)
		  AND receiver_id = :userId
		  AND (:type = 'ALL' OR type = :type)
		ORDER BY id
		LIMIT :size
	""", nativeQuery = true)
	List<Notification> findAllByUserIdWithType(
		@Param("userId") Long userId,
		@Param("type") String type,
		@Param("cursorId") Long cursorId,
		@Param("size") int size);
}
