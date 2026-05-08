package com.ufos.platform.modules.product.repository;

import com.ufos.platform.modules.product.domain.ProductRuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRuleRepository extends JpaRepository<ProductRuleEntity, UUID> {
    List<ProductRuleEntity> findByProductId(UUID productId);
}
