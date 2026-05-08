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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SecurityIntegrationTest {

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

    @Test
    public void testPublicEndpoints() throws Exception {
        mockMvc.perform(get("/api/v1/health")).andExpect(status().isOk());
        mockMvc.perform(get("/actuator/health")).andExpect(status().isOk());
        mockMvc.perform(get("/v3/api-docs")).andExpect(status().isOk());
    }

    @Test
    public void testProtectedEndpointRequiresAuth() throws Exception {
        mockMvc.perform(get("/api/v1/iam/me")).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "UFOS_ADMIN")
    public void testAdminCanAccessRoles() throws Exception {
        mockMvc.perform(get("/api/v1/iam/roles")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "UFOS_VIEWER")
    public void testViewerCannotAccessRoles() throws Exception {
        mockMvc.perform(get("/api/v1/iam/roles")).andExpect(status().isForbidden());
    }
}
