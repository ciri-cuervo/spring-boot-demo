package com.springdemo.web.command;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.springdemo.util.WebUtils;

public class SignupCommand {

	@Pattern(regexp = WebUtils.REGEX_USERNAME_PATTERN)
	private String username;

	@Pattern(regexp = WebUtils.REGEX_EMAIL_PATTERN)
	private String email;

	@Pattern(regexp = WebUtils.REGEX_PASSWORD_PATTERN)
	private String password;

	@NotEmpty
	private String repeatPassword;

	@AssertTrue
	private boolean accept;

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getRepeatPassword()
	{
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword)
	{
		this.repeatPassword = repeatPassword;
	}

	public boolean isAccept()
	{
		return accept;
	}

	public void setAccept(boolean accept)
	{
		this.accept = accept;
	}

	@Override
	public String toString()
	{
		return "SignupCommand [username=" + username + ", email=" + email + ", password="
				+ password + ", repeatPassword=" + repeatPassword + ", accept=" + accept + "]";
	}

}
