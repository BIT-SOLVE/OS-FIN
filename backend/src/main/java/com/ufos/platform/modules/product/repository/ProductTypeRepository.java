package com.ufos.platform.modules.product.repository;

import com.ufos.platform.modules.product.domain.ProductTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductTypeEntity, UUID> {
    Optional<ProductTypeEntity> findByCode(String code);
}
