package com.ufos.platform.modules.enterprise.repository;

import com.ufos.platform.modules.enterprise.domain.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, String> {
}
