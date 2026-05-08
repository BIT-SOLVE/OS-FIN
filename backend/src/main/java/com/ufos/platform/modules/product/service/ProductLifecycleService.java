package com.ufos.platform.modules.product.service;

import com.ufos.platform.modules.product.domain.ProductApprovalHistoryEntity;
import com.ufos.platform.modules.product.domain.ProductEntity;
import com.ufos.platform.modules.product.dto.ProductApprovalHistoryResponse;
import com.ufos.platform.modules.product.repository.ProductApprovalHistoryRepository;
import com.ufos.platform.modules.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductLifecycleService {

    private final ProductRepository productRepository;
    private final ProductApprovalHistoryRepository historyRepository;

    @Transactional
    public void submitForApproval(UUID id, String actor, String comment) {
        ProductEntity entity = findProduct(id);
        validateStatus(entity, "DRAFT");
        updateStatus(entity, "PENDING_APPROVAL", "SUBMIT", actor, comment);
    }

    @Transactional
    public void approveProduct(UUID id, String actor, String comment) {
        ProductEntity entity = findProduct(id);
        validateStatus(entity, "PENDING_APPROVAL");
        updateStatus(entity, "APPROVED", "APPROVE", actor, comment);
    }

    @Transactional
    public void rejectProduct(UUID id, String actor, String comment) {
        ProductEntity entity = findProduct(id);
        validateStatus(entity, "PENDING_APPROVAL");
        updateStatus(entity, "REJECTED", "REJECT", actor, comment);
    }

    @Transactional
    public void retireProduct(UUID id, String actor, String comment) {
        ProductEntity entity = findProduct(id);
        validateStatus(entity, "APPROVED");
        updateStatus(entity, "RETIRED", "RETIRE", actor, comment);
    }

    @Transactional(readOnly = true)
    public List<ProductApprovalHistoryResponse> getApprovalHistory(UUID productId) {
        return historyRepository.findByProductIdOrderByActedAtDesc(productId).stream()
                .map(h -> ProductApprovalHistoryResponse.builder()
                        .id(h.getId()).productId(h.getProductId()).action(h.getAction())
                        .previousStatus(h.getPreviousStatus()).newStatus(h.getNewStatus())
                        .comment(h.getComment()).actedBy(h.getActedBy()).actedAt(h.getActedAt())
                        .build())
                .collect(Collectors.toList());
    }

    private ProductEntity findProduct(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id " + id));
    }

    private void validateStatus(ProductEntity entity, String expected) {
        if (!expected.equals(entity.getLifecycleStatus())) {
            throw new IllegalArgumentException("Cannot perform action on product in " + entity.getLifecycleStatus() + " status");
        }
    }

    private void updateStatus(ProductEntity entity, String newStatus, String action, String actor, String comment) {
        String prevStatus = entity.getLifecycleStatus();
        entity.setLifecycleStatus(newStatus);
        productRepository.save(entity);

        ProductApprovalHistoryEntity history = ProductApprovalHistoryEntity.builder()
                .productId(entity.getId())
                .action(action)
                .previousStatus(prevStatus)
                .newStatus(newStatus)
                .comment(comment)
                .actedBy(actor)
                .build();
        historyRepository.save(history);
    }
}
