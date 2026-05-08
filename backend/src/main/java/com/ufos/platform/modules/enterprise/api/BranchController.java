package com.ufos.platform.modules.enterprise.api;

import com.ufos.platform.modules.enterprise.dto.BranchRequest;
import com.ufos.platform.modules.enterprise.dto.BranchResponse;
import com.ufos.platform.modules.enterprise.service.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/enterprise")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @PostMapping("/legal-entities/{legalEntityId}/branches")
    @PreAuthorize("hasRole('UFOS_ADMIN')")
    public BranchResponse createBranch(@PathVariable UUID legalEntityId, @Valid @RequestBody BranchRequest request) {
        return branchService.createBranch(legalEntityId, request);
    }

    @GetMapping("/legal-entities/{legalEntityId}/branches")
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR', 'UFOS_AUDITOR', 'UFOS_VIEWER')")
    public List<BranchResponse> getBranchesByLegalEntity(@PathVariable UUID legalEntityId) {
        return branchService.getBranchesByLegalEntity(legalEntityId);
    }

    @GetMapping("/branches/{id}")
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR', 'UFOS_AUDITOR', 'UFOS_VIEWER')")
    public BranchResponse getBranch(@PathVariable UUID id) {
        return branchService.getBranch(id);
    }

    @PutMapping("/branches/{id}")
    @PreAuthorize("hasRole('UFOS_ADMIN')")
    public BranchResponse updateBranch(@PathVariable UUID id, @Valid @RequestBody BranchRequest request) {
        return branchService.updateBranch(id, request);
    }
}
