package com.ufos.platform.modules.account.repository;

import com.ufos.platform.modules.account.domain.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
    Optional<AccountEntity> findByAccountNumber(String accountNumber);

    @Query("SELECT MAX(a.accountNumber) FROM AccountEntity a WHERE a.accountNumber LIKE 'A%'")
    String findMaxAccountNumber();
}
