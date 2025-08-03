package com.github.nenidan.ne_ne_challenge.domain.admin.domain.repository;

import com.github.nenidan.ne_ne_challenge.domain.admin.domain.model.AopLogModel;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.type.DomainType;
import com.github.nenidan.ne_ne_challenge.domain.admin.presentation.dto.request.LogSearchCond;

import java.util.Collection;
import java.util.List;

public interface AopLogRepository {
    List<AopLogModel> findLogs(DomainType domainType, LogSearchCond cond);
}
