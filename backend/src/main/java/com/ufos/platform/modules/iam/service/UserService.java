package com.ufos.platform.modules.iam.service;

import com.ufos.platform.modules.iam.domain.UserEntity;
import com.ufos.platform.modules.iam.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserEntity syncUserWithKeycloak(Jwt jwt) {
        String subject = jwt.getSubject();
        String username = jwt.getClaimAsString("preferred_username");
        String email = jwt.getClaimAsString("email");
        String givenName = jwt.getClaimAsString("given_name");
        String familyName = jwt.getClaimAsString("family_name");
        String displayName = (givenName != null ? givenName : "") + (familyName != null ? " " + familyName : "");

        UserEntity user = userRepository.findByKeycloakSubject(subject)
                .orElseGet(() -> UserEntity.builder()
                        .keycloakSubject(subject)
                        .username(username)
                        .status("ACTIVE")
                        .build());

        user.setUsername(username);
        user.setEmail(email);
        user.setDisplayName(displayName.trim());
        user.setLastLoginAt(LocalDateTime.now());

        return userRepository.save(user);
    }
}
