package com.ufos.platform.modules.account.repository;

import com.ufos.platform.modules.account.domain.AccountLifecycleHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountLifecycleHistoryRepository extends JpaRepository<AccountLifecycleHistoryEntity, UUID> {
    List<AccountLifecycleHistoryEntity> findByAccountIdOrderByActedAtDesc(UUID accountId);
}
