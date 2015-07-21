package com.springdemo.web.command;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.springdemo.domain.UserRepository;

@Component
public class SignupCommandValidator implements Validator {

	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean supports(Class<?> clazz)
	{
		return SignupCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors)
	{
		SignupCommand command = (SignupCommand) target;

		if (!errors.hasFieldErrors("username"))
		{
			if (userRepository.findByUsername(command.getUsername()) != null)
			{
				errors.rejectValue("username", "Exists");
			}
		}

		if (!errors.hasFieldErrors("email"))
		{
			command.setEmail(command.getEmail().toLowerCase(Locale.ENGLISH));

			if (userRepository.findByEmail(command.getEmail()) != null)
			{
				errors.rejectValue("email", "Exists");
			}
		}

		if (!errors.hasFieldErrors("username") && !errors.hasFieldErrors("password")
				&& command.getUsername().equals(command.getPassword()))
		{
			errors.rejectValue("password", "Invalid");
		}

		if (!errors.hasFieldErrors("password") && !errors.hasFieldErrors("repeatPassword")
				&& !command.getPassword().equals(command.getRepeatPassword()))
		{
			errors.rejectValue("repeatPassword", "DontMatch");
		}
	}

}
