package com.ufos.platform.modules.party.api;

import com.ufos.platform.modules.party.dto.CustomerOnboardingRequest;
import com.ufos.platform.modules.party.dto.CustomerRequest;
import com.ufos.platform.modules.party.dto.CustomerResponse;
import com.ufos.platform.modules.party.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/party/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR')")
    public CustomerResponse createCustomer(@Valid @RequestBody CustomerRequest request) {
        return customerService.createCustomer(request);
    }

    @PostMapping("/onboard")
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR')")
    public CustomerResponse onboardCustomer(@Valid @RequestBody CustomerOnboardingRequest request) {
        return customerService.onboardCustomer(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR', 'UFOS_AUDITOR', 'UFOS_VIEWER')")
    public List<CustomerResponse> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR', 'UFOS_AUDITOR', 'UFOS_VIEWER')")
    public CustomerResponse getCustomer(@PathVariable UUID id) {
        return customerService.getCustomer(id);
    }
}
