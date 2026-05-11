package com.ufos.platform.modules.enterprise.repository;

import com.ufos.platform.modules.enterprise.domain.BranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BranchRepository extends JpaRepository<BranchEntity, UUID> {
    List<BranchEntity> findByLegalEntityId(UUID legalEntityId);
    Optional<BranchEntity> findByLegalEntityIdAndCode(UUID legalEntityId, String code);
}
