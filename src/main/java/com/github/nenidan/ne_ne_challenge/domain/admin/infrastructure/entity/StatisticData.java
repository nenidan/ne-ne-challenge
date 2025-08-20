package com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.entity;

import java.time.LocalDate;

import com.github.nenidan.ne_ne_challenge.domain.admin.domain.type.DomainType;
import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "statistics_data",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_stats_type_month",
                columnNames = {"type", "stat_date"}
        ))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StatisticData extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private DomainType type;  // 통계 종류 구분용 enum

    private LocalDate statDate; // 월 anchor(1일)

    @Lob
    @Column(name = "payload", columnDefinition = "json", nullable = false)
    private String payload;

    @PrePersist @PreUpdate
    void normalizeMonth() {
        if (statDate != null) statDate = statDate.withDayOfMonth(1);
    }

}
