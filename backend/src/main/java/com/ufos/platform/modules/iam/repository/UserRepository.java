package com.ufos.platform.modules.iam.repository;

import com.ufos.platform.modules.iam.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByKeycloakSubject(String keycloakSubject);
    Optional<UserEntity> findByUsername(String username);
}
