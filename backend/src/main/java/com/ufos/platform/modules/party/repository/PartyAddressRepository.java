package com.ufos.platform.modules.party.repository;

import com.ufos.platform.modules.party.domain.PartyAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PartyAddressRepository extends JpaRepository<PartyAddressEntity, UUID> {
    List<PartyAddressEntity> findByPartyId(UUID partyId);
}
