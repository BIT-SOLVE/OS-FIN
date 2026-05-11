package com.ufos.platform.modules.enterprise.repository;

import com.ufos.platform.modules.enterprise.domain.BusinessCalendarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusinessCalendarRepository extends JpaRepository<BusinessCalendarEntity, UUID> {
    Optional<BusinessCalendarEntity> findByCode(String code);
}
