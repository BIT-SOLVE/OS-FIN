package com.ufos.platform.modules.product.service;

import com.ufos.platform.modules.product.domain.ProductEntity;
import com.ufos.platform.modules.product.dto.ProductRequest;
import com.ufos.platform.modules.product.dto.ProductResponse;
import com.ufos.platform.modules.product.repository.ProductRepository;
import com.ufos.platform.modules.product.repository.ProductTypeRepository;
import com.ufos.platform.modules.enterprise.repository.LegalEntityRepository;
import com.ufos.platform.modules.enterprise.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;
    private final LegalEntityRepository legalEntityRepository;
    private final CurrencyRepository currencyRepository;

    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        if (productRepository.findByProductCode(request.getProductCode().toUpperCase()).isPresent()) {
            throw new IllegalArgumentException("Product with code " + request.getProductCode() + " already exists");
        }

        validatePrerequisites(request);

        ProductEntity entity = ProductEntity.builder()
                .productCode(request.getProductCode().toUpperCase())
                .productName(request.getProductName())
                .productTypeId(request.getProductTypeId())
                .legalEntityId(request.getLegalEntityId())
                .currencyCode(request.getCurrencyCode() != null ? request.getCurrencyCode().toUpperCase() : null)
                .productFamily(request.getProductFamily())
                .productCategory(request.getProductCategory())
                .effectiveFrom(request.getEffectiveFrom())
                .effectiveTo(request.getEffectiveTo())
                .description(request.getDescription())
                .lifecycleStatus("DRAFT")
                .build();

        ProductEntity saved = productRepository.save(entity);
        return mapToResponse(saved);
    }

    @Transactional
    public ProductResponse updateProduct(UUID id, ProductRequest request) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id " + id));

        if ("RETIRED".equals(entity.getLifecycleStatus())) {
            throw new IllegalArgumentException("Cannot update a retired product");
        }

        validatePrerequisites(request);

        entity.setProductName(request.getProductName());
        entity.setProductTypeId(request.getProductTypeId());
        entity.setLegalEntityId(request.getLegalEntityId());
        entity.setCurrencyCode(request.getCurrencyCode() != null ? request.getCurrencyCode().toUpperCase() : null);
        entity.setProductFamily(request.getProductFamily());
        entity.setProductCategory(request.getProductCategory());
        entity.setEffectiveFrom(request.getEffectiveFrom());
        entity.setEffectiveTo(request.getEffectiveTo());
        entity.setDescription(request.getDescription());

        ProductEntity saved = productRepository.save(entity);
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductResponse getProduct(UUID id) {
        return productRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id " + id));
    }

    private void validatePrerequisites(ProductRequest request) {
        if (!productTypeRepository.existsById(request.getProductTypeId())) {
            throw new IllegalArgumentException("Product type not found with id " + request.getProductTypeId());
        }
        if (request.getLegalEntityId() != null && !legalEntityRepository.existsById(request.getLegalEntityId())) {
            throw new IllegalArgumentException("Legal entity not found with id " + request.getLegalEntityId());
        }
        if (request.getCurrencyCode() != null && !currencyRepository.existsById(request.getCurrencyCode().toUpperCase())) {
            throw new IllegalArgumentException("Currency not found with code " + request.getCurrencyCode());
        }
        if (request.getEffectiveTo() != null && request.getEffectiveFrom() != null && request.getEffectiveTo().isBefore(request.getEffectiveFrom())) {
            throw new IllegalArgumentException("Effective to date cannot be before effective from date");
        }
    }

    private ProductResponse mapToResponse(ProductEntity entity) {
        return ProductResponse.builder()
                .id(entity.getId())
                .productCode(entity.getProductCode())
                .productName(entity.getProductName())
                .productTypeId(entity.getProductTypeId())
                .legalEntityId(entity.getLegalEntityId())
                .currencyCode(entity.getCurrencyCode())
                .productFamily(entity.getProductFamily())
                .productCategory(entity.getProductCategory())
                .lifecycleStatus(entity.getLifecycleStatus())
                .effectiveFrom(entity.getEffectiveFrom())
                .effectiveTo(entity.getEffectiveTo())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
