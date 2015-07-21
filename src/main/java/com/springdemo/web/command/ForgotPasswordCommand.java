package com.springdemo.web.command;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.springdemo.util.WebUtils;

public class ForgotPasswordCommand {

	@Pattern(regexp = WebUtils.REGEX_EMAIL_PATTERN)
	private String email;

	@NotEmpty
	private String code;

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	@Override
	public String toString()
	{
		return "ForgotPasswordCommand [email=" + email + ", code=" + code + "]";
	}

}
