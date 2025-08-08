package com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.entity;

import com.github.nenidan.ne_ne_challenge.domain.admin.domain.type.DomainType;
import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "aop_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AopLog  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DomainType type;

    private String userId;

    @Column(nullable = false)
    private String method;

    @Column(columnDefinition = "TEXT")
    private String params;

    @Column(columnDefinition = "TEXT")
    private String result;


    // 생성자 (필드 모두 초기화)
    public AopLog(DomainType type, String method, String params, String result) {
        this.type = type;
        this.method = method;
        this.params = params;
        this.result = result;
    }

}
