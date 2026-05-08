package com.ufos.platform.modules.party.repository;

import com.ufos.platform.modules.party.domain.KycProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface KycProfileRepository extends JpaRepository<KycProfileEntity, UUID> {
    Optional<KycProfileEntity> findByCustomerId(UUID customerId);
}
