package com.ufos.platform.modules.party.api;

import com.ufos.platform.modules.party.dto.PartyRequest;
import com.ufos.platform.modules.party.dto.PartyResponse;
import com.ufos.platform.modules.party.service.PartyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/party/parties")
@RequiredArgsConstructor
public class PartyController {

    private final PartyService partyService;

    @PostMapping
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR')")
    public PartyResponse createParty(@Valid @RequestBody PartyRequest request) {
        return partyService.createParty(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR', 'UFOS_AUDITOR', 'UFOS_VIEWER')")
    public List<PartyResponse> getAllParties() {
        return partyService.getAllParties();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR', 'UFOS_AUDITOR', 'UFOS_VIEWER')")
    public PartyResponse getParty(@PathVariable UUID id) {
        return partyService.getParty(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR')")
    public PartyResponse updateParty(@PathVariable UUID id, @Valid @RequestBody PartyRequest request) {
        return partyService.updateParty(id, request);
    }
}
