package com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.respository;

import com.github.nenidan.ne_ne_challenge.domain.admin.domain.model.AopLogModel;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.repository.AopLogRepository;
import com.github.nenidan.ne_ne_challenge.domain.admin.domain.type.DomainType;
import com.github.nenidan.ne_ne_challenge.domain.admin.infrastructure.mapper.LogMapper;
import com.github.nenidan.ne_ne_challenge.domain.admin.application.dto.request.LogSearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AopLogRepositoryImpl implements AopLogRepository {

    private final LogTransactionRepository logTransactionRepository;

    @Override
   public List<AopLogModel> findLogs(DomainType type, LogSearchCond cond){
      return logTransactionRepository.findLogs(type, cond).stream().map(LogMapper::toDomain).toList();
   }
}
