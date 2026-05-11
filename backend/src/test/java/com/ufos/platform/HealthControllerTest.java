package com.ufos.platform;

import com.ufos.platform.modules.iam.repository.PermissionRepository;
import com.ufos.platform.modules.iam.repository.RoleRepository;
import com.ufos.platform.modules.iam.repository.UserRepository;
import com.ufos.platform.modules.iam.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class HealthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private PermissionRepository permissionRepository;

    @MockBean
    private com.ufos.platform.modules.enterprise.repository.LegalEntityRepository legalEntityRepository;

    @MockBean
    private com.ufos.platform.modules.enterprise.repository.BranchRepository branchRepository;

    @MockBean
    private com.ufos.platform.modules.enterprise.repository.CurrencyRepository currencyRepository;

    @MockBean
    private com.ufos.platform.modules.enterprise.repository.BusinessCalendarRepository businessCalendarRepository;

    @MockBean
    private com.ufos.platform.modules.enterprise.repository.CalendarHolidayRepository calendarHolidayRepository;

    @Test
    public void testHealthEndpoint() throws Exception {
        mockMvc.perform(get("/api/v1/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.service").value("ufos-platform"))
                .andExpect(jsonPath("$.version").value("0.1.0"));
    }

    @Test
    public void testCorrelationIdReturned() throws Exception {
        mockMvc.perform(get("/api/v1/health"))
                .andExpect(status().isOk())
                .andExpect(header().exists("X-Correlation-ID"));
    }

    @Test
    public void testCustomCorrelationIdReturned() throws Exception {
        String customId = "test-correlation-id";
        mockMvc.perform(get("/api/v1/health").header("X-Correlation-ID", customId))
                .andExpect(status().isOk())
                .andExpect(header().string("X-Correlation-ID", customId));
    }
}
