package com.ufos.platform.modules.product.repository;

import com.ufos.platform.modules.product.domain.ProductApprovalHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductApprovalHistoryRepository extends JpaRepository<ProductApprovalHistoryEntity, UUID> {
    List<ProductApprovalHistoryEntity> findByProductIdOrderByActedAtDesc(UUID productId);
}
