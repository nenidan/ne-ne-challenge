package com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.respository;

import com.github.nenidan.ne_ne_challenge.domain.admin.domain.model.AopLogModel;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.repository.AopLogRepository;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.type.DomainType;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.entity.AopLog;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.mapper.LogMapper;
import com.github.nenidan.ne_ne_challenge.domain.admin.presentation.dto.request.LogSearchCond;
import org.springframework.data.repository.query.Param;

import java.util.List;

public class AopLogRepositoryImpl implements AopLogRepository {

    private final LogTransactionRepository logTransactionRepository;

    public AopLogRepositoryImpl(LogTransactionRepository logTransactionRepository) {
        this.logTransactionRepository = logTransactionRepository;
    }

    @Override
   public List<AopLogModel> findLogs(DomainType type, LogSearchCond cond){
      return logTransactionRepository.findLogs(type, cond).stream().map(LogMapper::toDomain).toList();
   }
}
