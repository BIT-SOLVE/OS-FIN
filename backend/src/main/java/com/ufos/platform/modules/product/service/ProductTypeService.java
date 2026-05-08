package com.ufos.platform.modules.product.service;

import com.ufos.platform.modules.product.domain.ProductTypeEntity;
import com.ufos.platform.modules.product.dto.ProductTypeRequest;
import com.ufos.platform.modules.product.dto.ProductTypeResponse;
import com.ufos.platform.modules.product.repository.ProductTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductTypeService {

    private final ProductTypeRepository productTypeRepository;

    @Transactional
    public ProductTypeResponse createProductType(ProductTypeRequest request) {
        if (productTypeRepository.findByCode(request.getCode().toUpperCase()).isPresent()) {
            throw new IllegalArgumentException("Product type with code " + request.getCode() + " already exists");
        }

        ProductTypeEntity entity = ProductTypeEntity.builder()
                .code(request.getCode().toUpperCase())
                .name(request.getName())
                .description(request.getDescription())
                .productFamily(request.getProductFamily())
                .status(request.getStatus())
                .build();

        ProductTypeEntity saved = productTypeRepository.save(entity);
        return mapToResponse(saved);
    }

    @Transactional
    public ProductTypeResponse updateProductType(UUID id, ProductTypeRequest request) {
        ProductTypeEntity entity = productTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product type not found with id " + id));

        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setProductFamily(request.getProductFamily());
        entity.setStatus(request.getStatus());

        ProductTypeEntity saved = productTypeRepository.save(entity);
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<ProductTypeResponse> getAllProductTypes() {
        return productTypeRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductTypeResponse getProductType(UUID id) {
        return productTypeRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new IllegalArgumentException("Product type not found with id " + id));
    }

    private ProductTypeResponse mapToResponse(ProductTypeEntity entity) {
        return ProductTypeResponse.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .description(entity.getDescription())
                .productFamily(entity.getProductFamily())
                .status(entity.getStatus())
                .build();
    }
}
