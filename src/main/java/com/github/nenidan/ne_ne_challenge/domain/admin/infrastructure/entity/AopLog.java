package com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.entity;

import com.github.nenidan.ne_ne_challenge.domain.admin.domain.type.DomainType;
import com.github.nenidan.ne_ne_challenge.global.entity.BaseEntity;
import jakarta.persistence.*;
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

    private Long targetId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DomainType type;

    private boolean success;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String method;

    @Column(columnDefinition = "TEXT")
    private String params;

    @Column(columnDefinition = "TEXT")
    private String result;


    // 생성자
    public AopLog(DomainType type, Long targetId, String method, String params, String result, boolean success) {
        this.type = type;
        this.targetId = targetId;
        this.method = method;
        this.params = params;
        this.result = result;
        this.success = success;

    }

}
