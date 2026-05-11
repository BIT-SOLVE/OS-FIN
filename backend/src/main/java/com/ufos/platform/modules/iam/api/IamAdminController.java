package com.ufos.platform.modules.iam.api;

import com.ufos.platform.modules.iam.dto.PermissionResponse;
import com.ufos.platform.modules.iam.dto.RoleResponse;
import com.ufos.platform.modules.iam.repository.PermissionRepository;
import com.ufos.platform.modules.iam.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/iam")
@RequiredArgsConstructor
public class IamAdminController {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @GetMapping("/roles")
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_AUDITOR') or hasAuthority('ROLE_READ')")
    public List<RoleResponse> getRoles() {
        return roleRepository.findAll().stream()
                .map(r -> RoleResponse.builder()
                        .id(r.getId())
                        .code(r.getCode())
                        .name(r.getName())
                        .description(r.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    @GetMapping("/permissions")
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_AUDITOR') or hasAuthority('ROLE_READ')")
    public List<PermissionResponse> getPermissions() {
        return permissionRepository.findAll().stream()
                .map(p -> PermissionResponse.builder()
                        .id(p.getId())
                        .code(p.getCode())
                        .name(p.getName())
                        .description(p.getDescription())
                        .build())
                .collect(Collectors.toList());
    }
}
