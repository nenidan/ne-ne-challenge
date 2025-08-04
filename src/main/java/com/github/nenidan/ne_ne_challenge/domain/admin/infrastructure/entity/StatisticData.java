package com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.entity;

import com.github.nenidan.ne_ne_challenge.domain.admin.domain.type.DomainType;
import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "statistics_Data")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StatisticData extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private DomainType type;  // 통계 종류 구분용 enum

    private LocalDate statDate;

    @Column(name = "data1")
    private Long data1;

    @Column(name = "data2")
    private Long data2;

    @Column(name = "data3")
    private Long data3;

    @Column(name = "data4")
    private Long data4;

    @Column(name = "data5")
    private Long data5;
}
