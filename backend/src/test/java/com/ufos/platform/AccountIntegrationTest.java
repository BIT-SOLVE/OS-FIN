package com.ufos.platform;

import com.ufos.platform.modules.account.dto.*;
import com.ufos.platform.modules.account.repository.*;
import com.ufos.platform.modules.product.dto.ProductResponse;
import com.ufos.platform.modules.product.repository.*;
import com.ufos.platform.modules.party.dto.CustomerResponse;
import com.ufos.platform.modules.party.repository.*;
import com.ufos.platform.modules.enterprise.repository.*;
import com.ufos.platform.modules.iam.repository.*;
import com.ufos.platform.modules.iam.service.UserService;
import com.ufos.platform.modules.party.service.CustomerService;
import com.ufos.platform.modules.product.service.ProductService;
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

import java.math.BigDecimal;
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
public class AccountIntegrationTest {

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

    @MockBean private AccountRepository accountRepository;
    @MockBean private AccountBalanceRepository accountBalanceRepository;
    @MockBean private AccountBlockRepository accountBlockRepository;
    @MockBean private AccountLifecycleHistoryRepository accountLifecycleHistoryRepository;

    @MockBean private CustomerService customerService;
    @MockBean private ProductService productService;

    @Test
    @WithMockUser(roles = "UFOS_ADMIN")
    public void testOpenAccount() throws Exception {
        UUID customerId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID leId = UUID.randomUUID();

        when(customerService.getCustomer(customerId)).thenReturn(CustomerResponse.builder()
                .id(customerId).partyId(UUID.randomUUID()).customerStatus("ACTIVE").onboardingStatus("APPROVED").build());
        when(productService.getProduct(productId)).thenReturn(ProductResponse.builder()
                .id(productId).lifecycleStatus("APPROVED").productCategory("CASA").build());
        when(legalEntityRepository.existsById(leId)).thenReturn(true);

        when(accountRepository.save(any())).thenAnswer(i -> {
            com.ufos.platform.modules.account.domain.AccountEntity entity = i.getArgument(0);
            entity.setId(UUID.randomUUID());
            return entity;
        });

        AccountOpenRequest request = AccountOpenRequest.builder()
                .customerId(customerId)
                .productId(productId)
                .legalEntityId(leId)
                .currencyCode("USD")
                .accountType("CASA")
                .accountName("Test Account")
                .build();

        mockMvc.perform(post("/api/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountName").value("Test Account"));
    }
}
