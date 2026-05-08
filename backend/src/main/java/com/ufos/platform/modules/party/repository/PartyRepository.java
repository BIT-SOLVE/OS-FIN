package com.ufos.platform.modules.party.repository;

import com.ufos.platform.modules.party.domain.PartyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PartyRepository extends JpaRepository<PartyEntity, UUID> {
    Optional<PartyEntity> findByPartyNumber(String partyNumber);

    @Query("SELECT MAX(p.partyNumber) FROM PartyEntity p WHERE p.partyNumber LIKE 'P%'")
    String findMaxPartyNumber();
}
