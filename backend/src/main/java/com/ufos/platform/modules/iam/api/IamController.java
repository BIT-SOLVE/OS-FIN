package com.ufos.platform.modules.iam.api;

import com.ufos.platform.modules.iam.dto.UserMeResponse;
import com.ufos.platform.modules.iam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/iam")
@RequiredArgsConstructor
public class IamController {

    private final UserService userService;

    @GetMapping("/me")
    public UserMeResponse getCurrentUser(Authentication authentication) {
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            Jwt jwt = jwtAuth.getToken();
            userService.syncUserWithKeycloak(jwt);

            Set<String> authorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            Set<String> roles = authorities.stream()
                    .filter(a -> a.startsWith("ROLE_"))
                    .map(a -> a.substring(5))
                    .collect(Collectors.toSet());

            return UserMeResponse.builder()
                    .subject(jwt.getSubject())
                    .username(jwt.getClaimAsString("preferred_username"))
                    .email(jwt.getClaimAsString("email"))
                    .displayName(jwt.getClaimAsString("name"))
                    .roles(roles)
                    .authorities(authorities)
                    .authenticated(true)
                    .build();
        }

        return UserMeResponse.builder()
                .authenticated(false)
                .build();
    }
}
