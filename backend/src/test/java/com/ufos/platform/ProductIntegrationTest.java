package com.ufos.platform;

import com.ufos.platform.modules.product.dto.*;
import com.ufos.platform.modules.product.repository.*;
import com.ufos.platform.modules.enterprise.repository.*;
import com.ufos.platform.modules.iam.repository.*;
import com.ufos.platform.modules.iam.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean private UserService userService;
    @MockBean private UserRepository userRepository;
    @MockBean private RoleRepository roleRepository;
    @MockBean private PermissionRepository permissionRepository;
    @MockBean private LegalEntityRepository legalEntityRepository;
    @MockBean private BranchRepository branchRepository;
    @MockBean private CurrencyRepository currencyRepository;
    @MockBean private BusinessCalendarRepository businessCalendarRepository;
    @MockBean private CalendarHolidayRepository calendarHolidayRepository;
    @MockBean private PartyRepository partyRepository;
    @MockBean private CustomerRepository customerRepository;
    @MockBean private KycProfileRepository kycProfileRepository;

    @MockBean private ProductTypeRepository productTypeRepository;
    @MockBean private ProductRepository productRepository;
    @MockBean private ProductRuleRepository productRuleRepository;
    @MockBean private InterestRuleRepository interestRuleRepository;
    @MockBean private FeeRuleRepository feeRuleRepository;
    @MockBean private AccountingRuleRepository accountingRuleRepository;
    @MockBean private ProductApprovalHistoryRepository productApprovalHistoryRepository;

    @Test
    @WithMockUser(roles = "UFOS_ADMIN")
    public void testCreateProduct() throws Exception {
        UUID typeId = UUID.randomUUID();
        ProductRequest request = ProductRequest.builder()
                .productCode("CASA_USD")
                .productName("USD CASA")
                .productTypeId(typeId)
                .productFamily("DEPOSIT")
                .build();

        when(productTypeRepository.existsById(typeId)).thenReturn(true);
        when(productRepository.save(any())).thenAnswer(i -> {
            com.ufos.platform.modules.product.domain.ProductEntity entity = i.getArgument(0);
            entity.setId(UUID.randomUUID());
            return entity;
        });

        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productCode").value("CASA_USD"));
    }

    @Test
    @WithMockUser(roles = "UFOS_ADMIN")
    public void testApproveProduct() throws Exception {
        UUID productId = UUID.randomUUID();
        com.ufos.platform.modules.product.domain.ProductEntity entity = com.ufos.platform.modules.product.domain.ProductEntity.builder()
                .id(productId)
                .productCode("CASA_USD")
                .lifecycleStatus("PENDING_APPROVAL")
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(entity));

        ProductLifecycleActionRequest request = ProductLifecycleActionRequest.builder()
                .comment("Looks good")
                .build();

        mockMvc.perform(post("/api/v1/products/" + productId + "/approve")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
