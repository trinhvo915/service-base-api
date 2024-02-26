package com.service.api;

import com.service.api.domain.Role;
import com.service.api.domain.User;
import com.service.api.framework.constants.RoleConstant;
import com.service.api.repository.RoleRepository;
import com.service.api.repository.UserRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class ServiceBaseAPI implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${user.admin.password}")
	private String adminPassword;

	public static void main(String[] args) {
		SpringApplication.run(ServiceBaseAPI.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<User> users = userRepository.findAll();

		if(CollectionUtils.isEmpty(users)){
			List<String> roleParams = List.of(RoleConstant.ADMIN, RoleConstant.USER);
			Set<Role> roles = roleRepository.findByNameIn(roleParams);

			User user = User.builder()
					.email("admin@gmail.com")
					.username("admin")
					.mobile(null)
					.password(passwordEncoder.encode(adminPassword))
					.activated(true)
					.firstName("Admin")
					.createdBy("SYSTEM")
					.isDelete(false)
					.roles(roles)
					.build();
			userRepository.save(user);
		}
	}
}
