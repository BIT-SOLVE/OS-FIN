package com.ufos.platform.modules.enterprise.service;

import com.ufos.platform.modules.enterprise.domain.CurrencyEntity;
import com.ufos.platform.modules.enterprise.dto.CurrencyResponse;
import com.ufos.platform.modules.enterprise.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Transactional(readOnly = true)
    public List<CurrencyResponse> getAllCurrencies() {
        return currencyRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CurrencyResponse getCurrency(String code) {
        return currencyRepository.findById(code.toUpperCase())
                .map(this::mapToResponse)
                .orElseThrow(() -> new IllegalArgumentException("Currency not found with code " + code));
    }

    private CurrencyResponse mapToResponse(CurrencyEntity entity) {
        return CurrencyResponse.builder()
                .code(entity.getCode())
                .name(entity.getName())
                .numericCode(entity.getNumericCode())
                .minorUnits(entity.getMinorUnits())
                .status(entity.getStatus())
                .build();
    }
}
