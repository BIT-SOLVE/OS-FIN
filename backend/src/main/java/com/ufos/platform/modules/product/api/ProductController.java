package com.ufos.platform.modules.product.api;

import com.ufos.platform.modules.product.dto.*;
import com.ufos.platform.modules.product.service.ProductLifecycleService;
import com.ufos.platform.modules.product.service.ProductRuleService;
import com.ufos.platform.modules.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRuleService ruleService;
    private final ProductLifecycleService lifecycleService;

    @PostMapping
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR')")
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR', 'UFOS_AUDITOR', 'UFOS_VIEWER')")
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR', 'UFOS_AUDITOR', 'UFOS_VIEWER')")
    public ProductResponse getProduct(@PathVariable UUID id) {
        return productService.getProduct(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR')")
    public ProductResponse updateProduct(@PathVariable UUID id, @Valid @RequestBody ProductRequest request) {
        return productService.updateProduct(id, request);
    }

    // Rules
    @PostMapping("/{id}/interest-rules")
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR')")
    public InterestRuleResponse addInterestRule(@PathVariable UUID id, @Valid @RequestBody InterestRuleRequest request) {
        return ruleService.addInterestRule(id, request);
    }

    @GetMapping("/{id}/interest-rules")
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR', 'UFOS_AUDITOR')")
    public List<InterestRuleResponse> getInterestRules(@PathVariable UUID id) {
        return ruleService.getInterestRules(id);
    }

    // Lifecycle
    @PostMapping("/{id}/submit")
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR')")
    public void submitForApproval(@PathVariable UUID id, @RequestBody ProductLifecycleActionRequest request, Authentication auth) {
        lifecycleService.submitForApproval(id, auth.getName(), request.getComment());
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasRole('UFOS_ADMIN')")
    public void approveProduct(@PathVariable UUID id, @RequestBody ProductLifecycleActionRequest request, Authentication auth) {
        lifecycleService.approveProduct(id, auth.getName(), request.getComment());
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasRole('UFOS_ADMIN')")
    public void rejectProduct(@PathVariable UUID id, @RequestBody ProductLifecycleActionRequest request, Authentication auth) {
        lifecycleService.rejectProduct(id, auth.getName(), request.getComment());
    }

    @GetMapping("/{id}/approval-history")
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_AUDITOR')")
    public List<ProductApprovalHistoryResponse> getApprovalHistory(@PathVariable UUID id) {
        return lifecycleService.getApprovalHistory(id);
    }
}
