package com.springdemo.web.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.springdemo.domain.EmailHashRepository;

@Component
public class ChangePasswordCommandValidator implements Validator {

	@Autowired
	private EmailHashRepository emailHashRepository;

	@Override
	public boolean supports(Class<?> clazz)
	{
		return ChangePasswordCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors)
	{
		ChangePasswordCommand command = (ChangePasswordCommand) target;

		if (!errors.hasFieldErrors("hash"))
		{
			if (emailHashRepository.findByHashAndActiveTrue(command.getHash()) == null)
			{
				errors.reject("NotActive.changePasswordCommand");
			}
		}

		if (!errors.hasFieldErrors("password") && !errors.hasFieldErrors("repeatPassword")
				&& !command.getPassword().equals(command.getRepeatPassword()))
		{
			errors.rejectValue("repeatPassword", "DontMatch");
		}
	}

}
