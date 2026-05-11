package com.ufos.platform.modules.enterprise.api;

import com.ufos.platform.modules.enterprise.dto.CurrencyResponse;
import com.ufos.platform.modules.enterprise.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enterprise/currencies")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR', 'UFOS_AUDITOR', 'UFOS_VIEWER')")
    public List<CurrencyResponse> getAllCurrencies() {
        return currencyService.getAllCurrencies();
    }

    @GetMapping("/{code}")
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR', 'UFOS_AUDITOR', 'UFOS_VIEWER')")
    public CurrencyResponse getCurrency(@PathVariable String code) {
        return currencyService.getCurrency(code);
    }
}
