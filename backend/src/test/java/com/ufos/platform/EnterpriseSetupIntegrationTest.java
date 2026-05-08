package com.ufos.platform;

import com.ufos.platform.modules.enterprise.dto.LegalEntityRequest;
import com.ufos.platform.modules.enterprise.repository.*;
import com.ufos.platform.modules.iam.repository.PermissionRepository;
import com.ufos.platform.modules.iam.repository.RoleRepository;
import com.ufos.platform.modules.iam.repository.UserRepository;
import com.ufos.platform.modules.iam.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EnterpriseSetupIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private PermissionRepository permissionRepository;

    @MockBean
    private LegalEntityRepository legalEntityRepository;

    @MockBean
    private BranchRepository branchRepository;

    @MockBean
    private CurrencyRepository currencyRepository;

    @MockBean
    private BusinessCalendarRepository businessCalendarRepository;

    @MockBean
    private CalendarHolidayRepository calendarHolidayRepository;

    @Test
    @WithMockUser(roles = "UFOS_ADMIN")
    public void testCreateLegalEntity() throws Exception {
        LegalEntityRequest request = LegalEntityRequest.builder()
                .code("NEWBANK")
                .name("New Bank")
                .countryCode("US")
                .baseCurrencyCode("USD")
                .status("ACTIVE")
                .build();

        org.mockito.Mockito.when(legalEntityRepository.save(org.mockito.Mockito.any()))
                .thenAnswer(i -> {
                    com.ufos.platform.modules.enterprise.domain.LegalEntityEntity entity = i.getArgument(0);
                    entity.setId(UUID.randomUUID());
                    return entity;
                });

        mockMvc.perform(post("/api/v1/enterprise/legal-entities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("NEWBANK"));
    }

    @Test
    @WithMockUser(roles = "UFOS_VIEWER")
    public void testViewerCannotCreateLegalEntity() throws Exception {
        LegalEntityRequest request = LegalEntityRequest.builder()
                .code("VIEWBANK")
                .name("Viewer Bank")
                .countryCode("US")
                .baseCurrencyCode("USD")
                .status("ACTIVE")
                .build();

        mockMvc.perform(post("/api/v1/enterprise/legal-entities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "UFOS_VIEWER")
    public void testGetAllCurrencies() throws Exception {
        mockMvc.perform(get("/api/v1/enterprise/currencies"))
                .andExpect(status().isOk());
    }
}
