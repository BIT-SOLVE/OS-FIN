package com.ufos.platform.modules.product.service;

import com.ufos.platform.modules.product.domain.AccountingRuleEntity;
import com.ufos.platform.modules.product.domain.FeeRuleEntity;
import com.ufos.platform.modules.product.domain.InterestRuleEntity;
import com.ufos.platform.modules.product.domain.ProductRuleEntity;
import com.ufos.platform.modules.product.dto.*;
import com.ufos.platform.modules.product.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductRuleService {

    private final ProductRuleRepository productRuleRepository;
    private final InterestRuleRepository interestRuleRepository;
    private final FeeRuleRepository feeRuleRepository;
    private final AccountingRuleRepository accountingRuleRepository;

    // Generic Rules
    @Transactional
    public ProductRuleResponse addProductRule(UUID productId, ProductRuleRequest request) {
        ProductRuleEntity entity = ProductRuleEntity.builder()
                .productId(productId)
                .ruleType(request.getRuleType())
                .ruleCode(request.getRuleCode().toUpperCase())
                .ruleName(request.getRuleName())
                .ruleValue(request.getRuleValue())
                .ruleJson(request.getRuleJson())
                .status(request.getStatus())
                .effectiveFrom(request.getEffectiveFrom())
                .effectiveTo(request.getEffectiveTo())
                .build();
        return mapToResponse(productRuleRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<ProductRuleResponse> getProductRules(UUID productId) {
        return productRuleRepository.findByProductId(productId).stream()
                .map(this::mapToResponse).collect(Collectors.toList());
    }

    // Interest Rules
    @Transactional
    public InterestRuleResponse addInterestRule(UUID productId, InterestRuleRequest request) {
        InterestRuleEntity entity = InterestRuleEntity.builder()
                .productId(productId)
                .ruleCode(request.getRuleCode().toUpperCase())
                .interestType(request.getInterestType())
                .rateType(request.getRateType())
                .fixedRate(request.getFixedRate())
                .indexCode(request.getIndexCode())
                .spreadRate(request.getSpreadRate())
                .calculationBasis(request.getCalculationBasis())
                .compoundingFrequency(request.getCompoundingFrequency())
                .accrualFrequency(request.getAccrualFrequency())
                .status(request.getStatus())
                .effectiveFrom(request.getEffectiveFrom())
                .effectiveTo(request.getEffectiveTo())
                .build();
        return mapToInterestResponse(interestRuleRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<InterestRuleResponse> getInterestRules(UUID productId) {
        return interestRuleRepository.findByProductId(productId).stream()
                .map(this::mapToInterestResponse).collect(Collectors.toList());
    }

    // Fee Rules
    @Transactional
    public FeeRuleResponse addFeeRule(UUID productId, FeeRuleRequest request) {
        FeeRuleEntity entity = FeeRuleEntity.builder()
                .productId(productId)
                .ruleCode(request.getRuleCode().toUpperCase())
                .feeType(request.getFeeType())
                .feeAmount(request.getFeeAmount())
                .feeRate(request.getFeeRate())
                .currencyCode(request.getCurrencyCode() != null ? request.getCurrencyCode().toUpperCase() : null)
                .chargeEvent(request.getChargeEvent())
                .calculationMethod(request.getCalculationMethod())
                .status(request.getStatus())
                .effectiveFrom(request.getEffectiveFrom())
                .effectiveTo(request.getEffectiveTo())
                .build();
        return mapToFeeResponse(feeRuleRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<FeeRuleResponse> getFeeRules(UUID productId) {
        return feeRuleRepository.findByProductId(productId).stream()
                .map(this::mapToFeeResponse).collect(Collectors.toList());
    }

    // Accounting Rules
    @Transactional
    public AccountingRuleResponse addAccountingRule(UUID productId, AccountingRuleRequest request) {
        AccountingRuleEntity entity = AccountingRuleEntity.builder()
                .productId(productId)
                .ruleCode(request.getRuleCode().toUpperCase())
                .accountingEvent(request.getAccountingEvent())
                .debitGlPlaceholder(request.getDebitGlPlaceholder())
                .creditGlPlaceholder(request.getCreditGlPlaceholder())
                .description(request.getDescription())
                .status(request.getStatus())
                .effectiveFrom(request.getEffectiveFrom())
                .effectiveTo(request.getEffectiveTo())
                .build();
        return mapToAccountingResponse(accountingRuleRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public List<AccountingRuleResponse> getAccountingRules(UUID productId) {
        return accountingRuleRepository.findByProductId(productId).stream()
                .map(this::mapToAccountingResponse).collect(Collectors.toList());
    }

    private ProductRuleResponse mapToResponse(ProductRuleEntity entity) {
        return ProductRuleResponse.builder()
                .id(entity.getId()).productId(entity.getProductId()).ruleType(entity.getRuleType())
                .ruleCode(entity.getRuleCode()).ruleName(entity.getRuleName())
                .ruleValue(entity.getRuleValue()).ruleJson(entity.getRuleJson())
                .status(entity.getStatus()).effectiveFrom(entity.getEffectiveFrom())
                .effectiveTo(entity.getEffectiveTo()).build();
    }

    private InterestRuleResponse mapToInterestResponse(InterestRuleEntity entity) {
        return InterestRuleResponse.builder()
                .id(entity.getId()).productId(entity.getProductId()).ruleCode(entity.getRuleCode())
                .interestType(entity.getInterestType()).rateType(entity.getRateType())
                .fixedRate(entity.getFixedRate()).indexCode(entity.getIndexCode()).spreadRate(entity.getSpreadRate())
                .calculationBasis(entity.getCalculationBasis()).compoundingFrequency(entity.getCompoundingFrequency())
                .accrualFrequency(entity.getAccrualFrequency()).status(entity.getStatus())
                .effectiveFrom(entity.getEffectiveFrom()).effectiveTo(entity.getEffectiveTo()).build();
    }

    private FeeRuleResponse mapToFeeResponse(FeeRuleEntity entity) {
        return FeeRuleResponse.builder()
                .id(entity.getId()).productId(entity.getProductId()).ruleCode(entity.getRuleCode())
                .feeType(entity.getFeeType()).feeAmount(entity.getFeeAmount()).feeRate(entity.getFeeRate())
                .currencyCode(entity.getCurrencyCode()).chargeEvent(entity.getChargeEvent())
                .calculationMethod(entity.getCalculationMethod()).status(entity.getStatus())
                .effectiveFrom(entity.getEffectiveFrom()).effectiveTo(entity.getEffectiveTo()).build();
    }

    private AccountingRuleResponse mapToAccountingResponse(AccountingRuleEntity entity) {
        return AccountingRuleResponse.builder()
                .id(entity.getId()).productId(entity.getProductId()).ruleCode(entity.getRuleCode())
                .accountingEvent(entity.getAccountingEvent()).debitGlPlaceholder(entity.getDebitGlPlaceholder())
                .creditGlPlaceholder(entity.getCreditGlPlaceholder()).description(entity.getDescription())
                .status(entity.getStatus()).effectiveFrom(entity.getEffectiveFrom())
                .effectiveTo(entity.getEffectiveTo()).build();
    }
}
