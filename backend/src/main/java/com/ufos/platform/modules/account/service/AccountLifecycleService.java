package com.ufos.platform.modules.account.service;

import com.ufos.platform.modules.account.domain.AccountBalanceEntity;
import com.ufos.platform.modules.account.domain.AccountEntity;
import com.ufos.platform.modules.account.domain.AccountLifecycleHistoryEntity;
import com.ufos.platform.modules.account.dto.AccountLifecycleHistoryResponse;
import com.ufos.platform.modules.account.repository.AccountBalanceRepository;
import com.ufos.platform.modules.account.repository.AccountLifecycleHistoryRepository;
import com.ufos.platform.modules.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountLifecycleService {

    private final AccountRepository accountRepository;
    private final AccountBalanceRepository balanceRepository;
    private final AccountLifecycleHistoryRepository historyRepository;

    @Transactional
    public void freezeAccount(UUID id, String reason, String actor) {
        AccountEntity account = findAccount(id);
        validateNotClosed(account);

        String prevStatus = account.getAccountStatus();
        account.setAccountStatus("FROZEN");
        account.setFreezeReason(reason);
        accountRepository.save(account);

        recordHistory(id, "FREEZE", prevStatus, "FROZEN", reason, actor);
    }

    @Transactional
    public void unfreezeAccount(UUID id, String reason, String actor) {
        AccountEntity account = findAccount(id);
        if (!"FROZEN".equals(account.getAccountStatus())) {
            throw new IllegalArgumentException("Account is not frozen");
        }

        String prevStatus = account.getAccountStatus();
        account.setAccountStatus("ACTIVE");
        account.setFreezeReason(null);
        accountRepository.save(account);

        recordHistory(id, "UNFREEZE", prevStatus, "ACTIVE", reason, actor);
    }

    @Transactional
    public void closeAccount(UUID id, String reason, String actor) {
        AccountEntity account = findAccount(id);
        validateNotClosed(account);

        AccountBalanceEntity balance = balanceRepository.findByAccountId(id).orElseThrow();
        if (balance.getLedgerBalance().compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalArgumentException("Cannot close account with non-zero ledger balance");
        }
        if (balance.getBlockedBalance().compareTo(BigDecimal.ZERO) > 0) {
            throw new IllegalArgumentException("Cannot close account with active blocks");
        }

        String prevStatus = account.getAccountStatus();
        String prevLifecycle = account.getLifecycleStatus();

        account.setAccountStatus("CLOSED");
        account.setLifecycleStatus("CLOSED");
        account.setClosingDate(LocalDate.now());
        account.setClosureReason(reason);
        accountRepository.save(account);

        recordHistory(id, "CLOSE", prevLifecycle, "CLOSED", reason, actor);
    }

    @Transactional(readOnly = true)
    public List<AccountLifecycleHistoryResponse> getHistory(UUID accountId) {
        return historyRepository.findByAccountIdOrderByActedAtDesc(accountId).stream()
                .map(h -> AccountLifecycleHistoryResponse.builder()
                        .id(h.getId()).accountId(h.getAccountId()).action(h.getAction())
                        .previousStatus(h.getPreviousStatus()).newStatus(h.getNewStatus())
                        .reason(h.getReason()).actedBy(h.getActedBy()).actedAt(h.getActedAt())
                        .build())
                .collect(Collectors.toList());
    }

    private AccountEntity findAccount(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    private void validateNotClosed(AccountEntity account) {
        if ("CLOSED".equals(account.getAccountStatus())) {
            throw new IllegalArgumentException("Account is already closed");
        }
    }

    private void recordHistory(UUID accountId, String action, String prev, String next, String reason, String actor) {
        historyRepository.save(AccountLifecycleHistoryEntity.builder()
                .accountId(accountId).action(action).previousStatus(prev).newStatus(next)
                .reason(reason).actedBy(actor).build());
    }
}
