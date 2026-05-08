package com.ufos.platform.modules.account.repository;

import com.ufos.platform.modules.account.domain.AccountBalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountBalanceRepository extends JpaRepository<AccountBalanceEntity, UUID> {
    Optional<AccountBalanceEntity> findByAccountId(UUID accountId);
}
