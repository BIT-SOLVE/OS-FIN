package com.ufos.platform.modules.product.api;

import com.ufos.platform.modules.product.dto.ProductTypeRequest;
import com.ufos.platform.modules.product.dto.ProductTypeResponse;
import com.ufos.platform.modules.product.service.ProductTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products/types")
@RequiredArgsConstructor
public class ProductTypeController {

    private final ProductTypeService productTypeService;

    @PostMapping
    @PreAuthorize("hasRole('UFOS_ADMIN')")
    public ProductTypeResponse createProductType(@Valid @RequestBody ProductTypeRequest request) {
        return productTypeService.createProductType(request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR', 'UFOS_AUDITOR')")
    public List<ProductTypeResponse> getAllProductTypes() {
        return productTypeService.getAllProductTypes();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('UFOS_ADMIN', 'UFOS_OPERATOR', 'UFOS_AUDITOR')")
    public ProductTypeResponse getProductType(@PathVariable UUID id) {
        return productTypeService.getProductType(id);
    }
}
