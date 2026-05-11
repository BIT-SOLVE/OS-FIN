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
public class ErrorHandlingTest {

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
    public void testNotFoundReturnsCorrelationId() throws Exception {
        // Since we have Spring Security, a non-existent endpoint will be 401 if not permitted and no token.
        mockMvc.perform(get("/api/v1/non-existent"))
                .andExpect(status().isUnauthorized())
                .andExpect(header().exists("X-Correlation-ID"));
    }
}
