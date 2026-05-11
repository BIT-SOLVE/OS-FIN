package com.ufos.platform.modules.account.service;

import com.ufos.platform.modules.account.domain.AccountBalanceEntity;
import com.ufos.platform.modules.account.domain.AccountEntity;
import com.ufos.platform.modules.account.domain.AccountLifecycleHistoryEntity;
import com.ufos.platform.modules.account.dto.AccountOpenRequest;
import com.ufos.platform.modules.account.dto.AccountResponse;
import com.ufos.platform.modules.account.repository.AccountBalanceRepository;
import com.ufos.platform.modules.account.repository.AccountLifecycleHistoryRepository;
import com.ufos.platform.modules.account.repository.AccountRepository;
import com.ufos.platform.modules.party.dto.CustomerResponse;
import com.ufos.platform.modules.party.service.CustomerService;
import com.ufos.platform.modules.product.dto.ProductResponse;
import com.ufos.platform.modules.product.service.ProductService;
import com.ufos.platform.modules.enterprise.repository.LegalEntityRepository;
import com.ufos.platform.modules.enterprise.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountBalanceRepository balanceRepository;
    private final AccountLifecycleHistoryRepository historyRepository;
    private final CustomerService customerService;
    private final ProductService productService;
    private final LegalEntityRepository legalEntityRepository;
    private final BranchRepository branchRepository;

    @Transactional
    public AccountResponse openAccount(AccountOpenRequest request, String actor) {
        // Validate Customer
        CustomerResponse customer = customerService.getCustomer(request.getCustomerId());
        if (!"ACTIVE".equals(customer.getCustomerStatus()) || !"APPROVED".equals(customer.getOnboardingStatus())) {
            throw new IllegalArgumentException("Customer must be active and approved to open an account");
        }

        // Validate Product
        ProductResponse product = productService.getProduct(request.getProductId());
        if (!"APPROVED".equals(product.getLifecycleStatus()) || !"CASA".equals(product.getProductCategory())) {
            throw new IllegalArgumentException("Product must be an approved CASA product");
        }
        if (product.getCurrencyCode() != null && !product.getCurrencyCode().equals(request.getCurrencyCode())) {
            throw new IllegalArgumentException("Currency must match product currency");
        }

        // Validate Enterprise
        if (!legalEntityRepository.existsById(request.getLegalEntityId())) {
            throw new IllegalArgumentException("Legal entity not found");
        }
        if (request.getBranchId() != null && !branchRepository.existsById(request.getBranchId())) {
            throw new IllegalArgumentException("Branch not found");
        }

        String accountNumber = request.getAccountNumber();
        if (accountNumber == null || accountNumber.isBlank()) {
            accountNumber = generateAccountNumber();
        } else if (accountRepository.findByAccountNumber(accountNumber).isPresent()) {
            throw new IllegalArgumentException("Account number already exists");
        }

        AccountEntity account = AccountEntity.builder()
                .accountNumber(accountNumber)
                .customerId(customer.getId())
                .partyId(customer.getPartyId())
                .legalEntityId(request.getLegalEntityId())
                .branchId(request.getBranchId())
                .productId(product.getId())
                .currencyCode(request.getCurrencyCode())
                .accountType(request.getAccountType())
                .accountName(request.getAccountName())
                .accountStatus("ACTIVE")
                .lifecycleStatus("OPEN")
                .openingDate(LocalDate.now())
                .overdraftLimit(request.getOverdraftLimit())
                .minimumBalance(request.getMinimumBalance())
                .build();

        AccountEntity savedAccount = accountRepository.save(account);

        // Initialize Balance
        AccountBalanceEntity balance = AccountBalanceEntity.builder()
                .accountId(savedAccount.getId())
                .currencyCode(savedAccount.getCurrencyCode())
                .overdraftAvailable(savedAccount.getOverdraftLimit())
                .build();
        balanceRepository.save(balance);

        // Record History
        historyRepository.save(AccountLifecycleHistoryEntity.builder()
                .accountId(savedAccount.getId())
                .action("OPEN")
                .newStatus("OPEN")
                .reason("Account opening")
                .actedBy(actor)
                .build());

        return mapToResponse(savedAccount);
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AccountResponse getAccount(UUID id) {
        return accountRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    @Transactional(readOnly = true)
    public AccountResponse getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(this::mapToResponse)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    private String generateAccountNumber() {
        String maxNumber = accountRepository.findMaxAccountNumber();
        if (maxNumber == null) {
            return "A000000001";
        }
        int next = Integer.parseInt(maxNumber.substring(1)) + 1;
        return String.format("A%09d", next);
    }

    private AccountResponse mapToResponse(AccountEntity entity) {
        return AccountResponse.builder()
                .id(entity.getId())
                .accountNumber(entity.getAccountNumber())
                .customerId(entity.getCustomerId())
                .partyId(entity.getPartyId())
                .legalEntityId(entity.getLegalEntityId())
                .branchId(entity.getBranchId())
                .productId(entity.getProductId())
                .currencyCode(entity.getCurrencyCode())
                .accountType(entity.getAccountType())
                .accountName(entity.getAccountName())
                .accountStatus(entity.getAccountStatus())
                .lifecycleStatus(entity.getLifecycleStatus())
                .openingDate(entity.getOpeningDate())
                .closingDate(entity.getClosingDate())
                .overdraftLimit(entity.getOverdraftLimit())
                .minimumBalance(entity.getMinimumBalance())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
