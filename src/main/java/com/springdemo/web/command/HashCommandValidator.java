package com.springdemo.web.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.springdemo.domain.EmailHashRepository;

@Component
public class HashCommandValidator implements Validator {

	@Autowired
	private EmailHashRepository emailHashRepository;

	@Override
	public boolean supports(Class<?> clazz)
	{
		return HashCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors)
	{
		HashCommand command = (HashCommand) target;

		if (!errors.hasFieldErrors("hash"))
		{
			if (emailHashRepository.findByHashAndActiveTrue(command.getHash()) == null)
			{
				errors.reject("NotActive.hashCommand");
			}
		}
	}

}
