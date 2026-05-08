package com.ufos.platform.modules.account.repository;

import com.ufos.platform.modules.account.domain.AccountBlockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountBlockRepository extends JpaRepository<AccountBlockEntity, UUID> {
    List<AccountBlockEntity> findByAccountIdAndStatus(UUID accountId, String status);
    Optional<AccountBlockEntity> findByBlockReference(String blockReference);
}
