package com.example.sbwt.test.repository;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.sbwt.test.config.TestConfiguration;
import com.springdemo.domain.Role;
import com.springdemo.domain.RoleRepository;
import com.springdemo.domain.User;
import com.springdemo.domain.UserRepository;
import com.springdemo.domain.types.RoleAuthority;
import com.springdemo.domain.types.UserStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfiguration.class)
@Transactional
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	private User user;
	private Role roleAdmin;
	private Role roleUser;

	@Before
	public void setUp() throws Exception {
		roleAdmin = roleRepository.findByAuthority(RoleAuthority.ADMIN.toString());
		roleUser = roleRepository.findByAuthority(RoleAuthority.USER.toString());

		user = new User();
		user.setUsername("testuser");
		user.setPassword("testpassword");
		user.setEmail("testuser@email.com");
		user.setStatus(UserStatus.ACTIVE);
		user.addAuthority(roleUser);
	}

	@Test
	public void testCreateUser() {
		user = userRepository.save(user);
		User actual = userRepository.findByUsername(user.getUsername());

		assertNotNull(actual);
		assertEquals(user.getId(), actual.getId());
		assertEquals(user.getEmail(), actual.getEmail());
		assertEquals(1, actual.getAuthorities().size());
		assertEquals(RoleAuthority.USER.toString(), actual.getAuthorities().get(0).getAuthority());
		assertNotNull(actual.getCreatedDate());
		assertNotNull(actual.getLastModifiedDate());
	}

	@Test
	public void testDeleteUser() {
		user = userRepository.save(user);
		userRepository.delete(user);
		User actual = userRepository.findByUsername(user.getUsername());

		assertNull(actual);
	}

	@Test
	public void testEditRoles() {
		user.addAuthority(roleAdmin);
		user = userRepository.save(user);

		assertEquals(2, user.getAuthorities().size());
		assertTrue(user.getAuthorities().contains(roleAdmin));

		user.removeAuthority(roleAdmin);
		user = userRepository.save(user);

		assertEquals(1, user.getAuthorities().size());
		assertFalse(user.getAuthorities().contains(roleAdmin));
	}

}
