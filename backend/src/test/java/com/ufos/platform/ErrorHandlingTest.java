package com.ufos.platform;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Test
    public void testNotFoundReturnsCorrelationId() throws Exception {
        // Since we have Spring Security, a non-existent endpoint will be 403 or 401 if not permitted.
        // But /api/v1/health is permitted. Let's try an unpermitted one.
        mockMvc.perform(get("/api/v1/non-existent"))
                .andExpect(status().isForbidden())
                // Spring Security might not trigger our GlobalExceptionHandler for 403 before reaching controller.
                // But CorrelationIdFilter should still add the header.
                .andExpect(header().exists("X-Correlation-ID"));
    }
}
