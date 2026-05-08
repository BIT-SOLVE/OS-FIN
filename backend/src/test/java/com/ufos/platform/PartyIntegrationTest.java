package com.ufos.platform;

import com.ufos.platform.modules.enterprise.repository.*;
import com.ufos.platform.modules.party.dto.*;
import com.ufos.platform.modules.party.repository.*;
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
public class PartyIntegrationTest {

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

    @Test
    @WithMockUser(roles = "UFOS_ADMIN")
    public void testCreateParty() throws Exception {
        PartyRequest request = PartyRequest.builder()
                .partyType("INDIVIDUAL")
                .displayName("John Doe")
                .status("ACTIVE")
                .build();

        when(partyRepository.save(any())).thenAnswer(i -> {
            com.ufos.platform.modules.party.domain.PartyEntity entity = i.getArgument(0);
            entity.setId(UUID.randomUUID());
            return entity;
        });

        mockMvc.perform(post("/api/v1/party/parties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.displayName").value("John Doe"));
    }

    @Test
    @WithMockUser(roles = "UFOS_ADMIN")
    public void testOnboardCustomer() throws Exception {
        UUID leId = UUID.randomUUID();
        CustomerOnboardingRequest request = CustomerOnboardingRequest.builder()
                .party(PartyRequest.builder().partyType("INDIVIDUAL").displayName("Onboarded User").status("ACTIVE").build())
                .customer(CustomerRequest.builder().legalEntityId(leId).customerType("RETAIL").riskRating("LOW").onboardingStatus("PENDING").customerStatus("INACTIVE").build())
                .kyc(KycProfileRequest.builder().kycStatus("PENDING").build())
                .build();

        when(legalEntityRepository.existsById(leId)).thenReturn(true);
        when(partyRepository.save(any())).thenAnswer(i -> {
            com.ufos.platform.modules.party.domain.PartyEntity entity = i.getArgument(0);
            entity.setId(UUID.randomUUID());
            return entity;
        });
        when(customerRepository.save(any())).thenAnswer(i -> {
            com.ufos.platform.modules.party.domain.CustomerEntity entity = i.getArgument(0);
            entity.setId(UUID.randomUUID());
            return entity;
        });

        mockMvc.perform(post("/api/v1/party/customers/onboard")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}
