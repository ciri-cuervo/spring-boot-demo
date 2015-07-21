package com.springdemo.service;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springdemo.domain.EmailHash;
import com.springdemo.domain.EmailHashRepository;
import com.springdemo.domain.RoleRepository;
import com.springdemo.domain.User;
import com.springdemo.domain.UserRepository;
import com.springdemo.domain.types.EmailType;
import com.springdemo.domain.types.RoleAuthority;
import com.springdemo.domain.types.UserStatus;
import com.springdemo.util.WebUtils;
import com.springdemo.web.command.ChangePasswordCommand;
import com.springdemo.web.command.HashCommand;
import com.springdemo.web.command.SignupCommand;

@Service
@Transactional
public class RegistrationService {

	@Autowired
	private Environment environment;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private EmailHashRepository emailHashRepository;

	private long hashExpireDuration;

	@PostConstruct
	public void postConstruct()
	{
		hashExpireDuration = environment.getProperty("application.hash.expire.duration",
				Long.class, 2L);
	}

	/*
	 * Activate user
	 */

	public void activateUser(HashCommand command)
	{
		EmailHash emailHash = emailHashRepository.findByHash(command.getHash());
		User user = userRepository.findByEmail(emailHash.getEmail());
		user.setStatus(UserStatus.ACTIVE);
		user = userRepository.save(user);
		emailHashRepository.deactivateByEmail(emailHash.getEmail());
	}

	/*
	 * Change password
	 */

	public void changePassword(ChangePasswordCommand command)
	{
		EmailHash emailHash = emailHashRepository.findByHash(command.getHash());
		User user = userRepository.findByEmail(emailHash.getEmail());
		user.setPassword(command.getPassword());
		user = userRepository.save(user);
		emailHashRepository.deactivateByEmail(emailHash.getEmail());
	}

	/*
	 * Create new user
	 */

	public EmailHash createUser(SignupCommand signupCommand)
	{
		User user = new User();
		user.setUsername(signupCommand.getUsername());
		user.setEmail(signupCommand.getEmail());
		user.setPassword(signupCommand.getPassword());
		user.addAuthority(roleRepository.findByAuthority(RoleAuthority.USER.toString()));
		user.setStatus(UserStatus.WAITING_ACTIVATION);
		user = userRepository.save(user);
		return createEmailHash(signupCommand.getEmail(), EmailType.ACTIVATION);
	}

	/*
	 * Create email hash
	 */

	public EmailHash createEmailHash(String email, EmailType emailType)
	{
		EmailHash emailHash = new EmailHash();
		emailHash.setEmail(email);
		emailHash.setHash(WebUtils.createHash());
		emailHash.setType(emailType);
		emailHash.setActive(true);
		emailHash.setExpire(LocalDateTime.now().plusHours(hashExpireDuration));
		emailHashRepository.deactivateByEmail(emailHash.getEmail());
		return emailHashRepository.save(emailHash);
	}

	/*
	 * Delete a user and its dependencies
	 */

	public void deleteUser(User user)
	{
		if (user != null)
		{
			userRepository.delete(user);
		}
	}

}
