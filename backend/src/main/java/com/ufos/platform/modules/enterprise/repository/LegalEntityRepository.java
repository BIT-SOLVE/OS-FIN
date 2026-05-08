package com.ufos.platform.modules.enterprise.repository;

import com.ufos.platform.modules.enterprise.domain.LegalEntityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LegalEntityRepository extends JpaRepository<LegalEntityEntity, UUID> {
    Optional<LegalEntityEntity> findByCode(String code);
}
