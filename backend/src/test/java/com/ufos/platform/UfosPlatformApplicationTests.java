package com.ufos.platform;

import com.ufos.platform.modules.iam.repository.PermissionRepository;
import com.ufos.platform.modules.iam.repository.RoleRepository;
import com.ufos.platform.modules.iam.repository.UserRepository;
import com.ufos.platform.modules.iam.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class UfosPlatformApplicationTests {

	@MockBean
	private UserService userService;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private RoleRepository roleRepository;

	@MockBean
	private PermissionRepository permissionRepository;

	@Test
	void contextLoads() {
	}

}
