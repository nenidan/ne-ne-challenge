package com.github.nenidan.ne_ne_challenge.domain.admin.entity;

import com.github.nenidan.ne_ne_challenge.domain.admin.type.LogType;
import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private LogType type;

    @Column(nullable = false)
    private String message;

    private String userId;

    @Column(nullable = false)
    private String method;

    @Column(columnDefinition = "TEXT")
    private String params;

    @Column(columnDefinition = "TEXT")
    private String result;

    // 생성자 (필드 모두 초기화)
    public AopLog(LogType type, String method, String params, String result) {
        this.type = type;
        this.method = method;
        this.params = params;
        this.result = result;
    }

}
