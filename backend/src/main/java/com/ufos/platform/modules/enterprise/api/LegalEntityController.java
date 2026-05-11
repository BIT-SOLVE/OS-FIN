package com.ufos.platform.modules.enterprise.api;

import com.ufos.platform.modules.enterprise.dto.LegalEntityRequest;
import com.ufos.platform.modules.enterprise.dto.LegalEntityResponse;
import com.ufos.platform.modules.enterprise.service.LegalEntityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/enterprise/legal-entities")
@RequiredArgsConstructor
public class LegalEntityController {

    private final LegalEntityService legalEntityService;

    @PostMapping
    @PreAuthorize("hasRole('UFOS_ADMIN')")
    public LegalEntityResponse createLegalEntity(@Valid @RequestBody LegalEntityRequest request) {
        return legalEntityService.createLegalEntity(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR', 'UFOS_AUDITOR', 'UFOS_VIEWER')")
    public List<LegalEntityResponse> getAllLegalEntities() {
        return legalEntityService.getAllLegalEntities();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR', 'UFOS_AUDITOR', 'UFOS_VIEWER')")
    public LegalEntityResponse getLegalEntity(@PathVariable UUID id) {
        return legalEntityService.getLegalEntity(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('UFOS_ADMIN')")
    public LegalEntityResponse updateLegalEntity(@PathVariable UUID id, @Valid @RequestBody LegalEntityRequest request) {
        return legalEntityService.updateLegalEntity(id, request);
    }
}
