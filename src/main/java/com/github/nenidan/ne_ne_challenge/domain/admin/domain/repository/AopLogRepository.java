package com.github.nenidan.ne_ne_challenge.domain.admin.domain.repository;

import java.util.List;

import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.request.LogSearchCond;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.model.AopLogModel;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.type.DomainType;

public interface AopLogRepository {
    List<AopLogModel> findLogs(DomainType domainType, LogSearchCond cond);
}
