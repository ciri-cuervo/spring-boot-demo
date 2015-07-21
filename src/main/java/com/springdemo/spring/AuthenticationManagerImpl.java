package com.springdemo.spring;

import java.util.regex.Pattern;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.springdemo.domain.User;
import com.springdemo.domain.types.UserStatus;
import com.springdemo.service.DefaultUserDetailsService;
import com.springdemo.util.WebUtils;

public class AuthenticationManagerImpl implements AuthenticationManager {

	private static final Pattern USERNAME_PATTERN = Pattern.compile(WebUtils.REGEX_USERNAME_PATTERN);

	private DefaultUserDetailsService userDetailsService;
	private BCryptPasswordEncoder passwordEncoder;

	public AuthenticationManagerImpl(DefaultUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
		this.passwordEncoder = new BCryptPasswordEncoder(8);
	}

	@Override
	@Transactional(readOnly = true)
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		User user;

		if (!USERNAME_PATTERN.matcher(authentication.getName()).matches()) {
			throw new BadCredentialsException("Invalid.login.usernamepassword");
		}

		try {
			user = userDetailsService.loadUserByUsername(authentication.getName());
		} catch (UsernameNotFoundException ex) {
			throw new BadCredentialsException("Invalid.login.usernamepassword");
		}

		if (UserStatus.WAITING_ACTIVATION.equals(user.getStatus())) {
			throw new BadCredentialsException("WaitingActivation.login.user");
		}

		if (!passwordEncoder.matches((String) authentication.getCredentials(), user.getPassword())) {
			throw new BadCredentialsException("Invalid.login.usernamepassword");
		}

		// TODO move to authentication success handler
		// emailHashRepository.deactivateByEmail(user.getEmail());

		return new UsernamePasswordAuthenticationToken(user, user, user.getAuthorities());
	}

}
