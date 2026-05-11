package com.ufos.platform.modules.account.api;

import com.ufos.platform.modules.account.dto.*;
import com.ufos.platform.modules.account.service.AccountBalanceService;
import com.ufos.platform.modules.account.service.AccountLifecycleService;
import com.ufos.platform.modules.account.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountBalanceService balanceService;
    private final AccountLifecycleService lifecycleService;

    @PostMapping
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR')")
    public AccountResponse openAccount(@Valid @RequestBody AccountOpenRequest request, Authentication auth) {
        return accountService.openAccount(request, auth.getName());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR', 'UFOS_AUDITOR', 'UFOS_VIEWER')")
    public List<AccountResponse> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR', 'UFOS_AUDITOR', 'UFOS_VIEWER')")
    public AccountResponse getAccount(@PathVariable UUID id) {
        return accountService.getAccount(id);
    }

    @GetMapping("/by-number/{accountNumber}")
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR', 'UFOS_AUDITOR', 'UFOS_VIEWER')")
    public AccountResponse getAccountByNumber(@PathVariable String accountNumber) {
        return accountService.getAccountByNumber(accountNumber);
    }

    @GetMapping("/{id}/balance")
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR', 'UFOS_AUDITOR', 'UFOS_VIEWER')")
    public AccountBalanceResponse getBalance(@PathVariable UUID id) {
        return balanceService.getBalance(id);
    }

    @PostMapping("/{id}/freeze")
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR')")
    public void freezeAccount(@PathVariable UUID id, @RequestBody AccountLifecycleRequest request, Authentication auth) {
        lifecycleService.freezeAccount(id, request.getReason(), auth.getName());
    }

    @PostMapping("/{id}/unfreeze")
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR')")
    public void unfreezeAccount(@PathVariable UUID id, @RequestBody AccountLifecycleRequest request, Authentication auth) {
        lifecycleService.unfreezeAccount(id, request.getReason(), auth.getName());
    }

    @PostMapping("/{id}/close")
    @PreAuthorize("hasRole('UFOS_ADMIN')")
    public void closeAccount(@PathVariable UUID id, @RequestBody AccountLifecycleRequest request, Authentication auth) {
        lifecycleService.closeAccount(id, request.getReason(), auth.getName());
    }

    @GetMapping("/{id}/lifecycle-history")
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_AUDITOR')")
    public List<AccountLifecycleHistoryResponse> getHistory(@PathVariable UUID id) {
        return lifecycleService.getHistory(id);
    }

    // Blocks
    @PostMapping("/{id}/blocks")
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR')")
    public AccountBlockResponse placeBlock(@PathVariable UUID id, @Valid @RequestBody AccountBlockRequest request, Authentication auth) {
        return balanceService.placeBlock(id, request, auth.getName());
    }

    @GetMapping("/{id}/blocks")
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR', 'UFOS_AUDITOR')")
    public List<AccountBlockResponse> getBlocks(@PathVariable UUID id) {
        return balanceService.getBlocks(id);
    }

    @PostMapping("/blocks/{blockId}/release")
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR')")
    public void releaseBlock(@PathVariable UUID blockId, Authentication auth) {
        balanceService.releaseBlock(blockId, auth.getName());
    }
}
