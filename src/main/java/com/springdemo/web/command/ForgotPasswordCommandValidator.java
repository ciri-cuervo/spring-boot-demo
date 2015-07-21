package com.springdemo.web.command;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.springdemo.domain.User;
import com.springdemo.domain.UserRepository;
import com.springdemo.domain.types.UserStatus;

@Component
public class ForgotPasswordCommandValidator implements Validator {

	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean supports(Class<?> clazz)
	{
		return ForgotPasswordCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors)
	{
		ForgotPasswordCommand command = (ForgotPasswordCommand) target;

		if (!errors.hasFieldErrors("email"))
		{
			command.setEmail(command.getEmail().toLowerCase(Locale.ENGLISH));

			User user = userRepository.findByEmail(command.getEmail());
			if (user == null || UserStatus.WAITING_ACTIVATION.equals(user.getStatus()))
			{
				errors.rejectValue("email", "NotExists");
			}
		}
	}

}
