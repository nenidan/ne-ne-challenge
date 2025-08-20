package com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.respository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.request.LogSearchCond;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.model.AopLogModel;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.repository.AopLogRepository;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.type.DomainType;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.mapper.LogMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AopLogRepositoryImpl implements AopLogRepository {

    private final LogTransactionRepository logTransactionRepository;

    @Override
   public List<AopLogModel> findLogs(DomainType type, LogSearchCond cond){
      return logTransactionRepository.findLogs(type, cond).stream().map(LogMapper::toDomain).toList();
   }
}
