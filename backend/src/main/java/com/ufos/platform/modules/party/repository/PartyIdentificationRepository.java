package com.ufos.platform.modules.party.repository;

import com.ufos.platform.modules.party.domain.PartyIdentificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PartyIdentificationRepository extends JpaRepository<PartyIdentificationEntity, UUID> {
    List<PartyIdentificationEntity> findByPartyId(UUID partyId);
}
