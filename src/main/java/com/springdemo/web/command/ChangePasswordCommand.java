package com.springdemo.web.command;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.springdemo.util.WebUtils;

public class ChangePasswordCommand {

	@Pattern(regexp = WebUtils.REGEX_HASH_PATTERN)
	private String hash;

	@Pattern(regexp = WebUtils.REGEX_PASSWORD_PATTERN)
	private String password;

	@NotEmpty
	private String repeatPassword;

	public String getHash()
	{
		return hash;
	}

	public void setHash(String hash)
	{
		this.hash = hash;
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

	@Override
	public String toString()
	{
		return "ChangePasswordCommand [hash=" + hash + ", password=" + password
				+ ", repeatPassword=" + repeatPassword + "]";
	}

}
