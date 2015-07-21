package com.springdemo.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springdemo.domain.User;
import com.springdemo.domain.UserRepository;

@Service
public class DefaultUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException
	{
		User user;

		if (username.contains("@"))
		{
			username = username.toLowerCase(Locale.ENGLISH);
			user = userRepository.findByEmail(username);
		} else
		{
			user = userRepository.findByUsername(username);
		}

		if (user == null)
		{
			throw new UsernameNotFoundException(username + " not found");
		}

		return user;
	}

}
