package com.ufos.platform.modules.account.service;

import com.ufos.platform.modules.account.domain.AccountBalanceEntity;
import com.ufos.platform.modules.account.domain.AccountBlockEntity;
import com.ufos.platform.modules.account.domain.AccountEntity;
import com.ufos.platform.modules.account.dto.AccountBalanceResponse;
import com.ufos.platform.modules.account.dto.AccountBlockRequest;
import com.ufos.platform.modules.account.dto.AccountBlockResponse;
import com.ufos.platform.modules.account.repository.AccountBalanceRepository;
import com.ufos.platform.modules.account.repository.AccountBlockRepository;
import com.ufos.platform.modules.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountBalanceService {

    private final AccountBalanceRepository balanceRepository;
    private final AccountBlockRepository blockRepository;
    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public AccountBalanceResponse getBalance(UUID accountId) {
        return balanceRepository.findByAccountId(accountId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new IllegalArgumentException("Balance not found for account"));
    }

    @Transactional
    public AccountBlockResponse placeBlock(UUID accountId, AccountBlockRequest request, String actor) {
        AccountBalanceEntity balance = balanceRepository.findByAccountId(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Balance not found"));

        if (request.getBlockAmount().compareTo(balance.getAvailableBalance()) > 0) {
            throw new IllegalArgumentException("Insufficient available balance to place block");
        }

        AccountBlockEntity block = AccountBlockEntity.builder()
                .accountId(accountId)
                .blockReference(request.getBlockReference())
                .blockAmount(request.getBlockAmount())
                .blockReason(request.getBlockReason())
                .status("ACTIVE")
                .createdBy(actor)
                .build();

        AccountBlockEntity saved = blockRepository.save(block);

        // Update Balance
        balance.setBlockedBalance(balance.getBlockedBalance().add(request.getBlockAmount()));
        recalculateAvailableBalance(balance);
        balanceRepository.save(balance);

        return mapToBlockResponse(saved);
    }

    @Transactional
    public void releaseBlock(UUID blockId, String actor) {
        AccountBlockEntity block = blockRepository.findById(blockId)
                .orElseThrow(() -> new IllegalArgumentException("Block not found"));

        if (!"ACTIVE".equals(block.getStatus())) {
            throw new IllegalArgumentException("Block is not active");
        }

        block.setStatus("RELEASED");
        block.setReleasedAt(LocalDateTime.now());
        block.setReleasedBy(actor);
        blockRepository.save(block);

        AccountBalanceEntity balance = balanceRepository.findByAccountId(block.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Balance not found"));

        balance.setBlockedBalance(balance.getBlockedBalance().subtract(block.getBlockAmount()));
        recalculateAvailableBalance(balance);
        balanceRepository.save(balance);
    }

    @Transactional(readOnly = true)
    public List<AccountBlockResponse> getBlocks(UUID accountId) {
        return blockRepository.findByAccountIdAndStatus(accountId, "ACTIVE").stream()
                .map(this::mapToBlockResponse)
                .collect(Collectors.toList());
    }

    private void recalculateAvailableBalance(AccountBalanceEntity balance) {
        AccountEntity account = accountRepository.findById(balance.getAccountId()).orElseThrow();
        // Formula: available = ledger + overdraft - blocked - uncleared
        BigDecimal available = balance.getLedgerBalance()
                .add(account.getOverdraftLimit())
                .subtract(balance.getBlockedBalance())
                .subtract(balance.getUnclearedBalance());

        balance.setAvailableBalance(available);
        balance.setOverdraftAvailable(account.getOverdraftLimit().add(balance.getLedgerBalance().min(BigDecimal.ZERO))); // Simplified
        balance.setLastUpdatedAt(LocalDateTime.now());
    }

    private AccountBalanceResponse mapToResponse(AccountBalanceEntity entity) {
        return AccountBalanceResponse.builder()
                .id(entity.getId())
                .accountId(entity.getAccountId())
                .currencyCode(entity.getCurrencyCode())
                .ledgerBalance(entity.getLedgerBalance())
                .availableBalance(entity.getAvailableBalance())
                .blockedBalance(entity.getBlockedBalance())
                .unclearedBalance(entity.getUnclearedBalance())
                .overdraftAvailable(entity.getOverdraftAvailable())
                .lastUpdatedAt(entity.getLastUpdatedAt())
                .build();
    }

    private AccountBlockResponse mapToBlockResponse(AccountBlockEntity entity) {
        return AccountBlockResponse.builder()
                .id(entity.getId())
                .accountId(entity.getAccountId())
                .blockReference(entity.getBlockReference())
                .blockAmount(entity.getBlockAmount())
                .blockReason(entity.getBlockReason())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .releasedAt(entity.getReleasedAt())
                .createdBy(entity.getCreatedBy())
                .releasedBy(entity.getReleasedBy())
                .build();
    }
}
