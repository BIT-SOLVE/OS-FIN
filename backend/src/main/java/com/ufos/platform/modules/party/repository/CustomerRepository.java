package com.ufos.platform.modules.party.repository;

import com.ufos.platform.modules.party.domain.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {
    Optional<CustomerEntity> findByCustomerNumber(String customerNumber);

    @Query("SELECT MAX(c.customerNumber) FROM CustomerEntity c WHERE c.customerNumber LIKE 'C%'")
    String findMaxCustomerNumber();
}
