package com.springdemo.web.command;

import javax.validation.constraints.Pattern;

import com.springdemo.util.WebUtils;

public class HashCommand {

	@Pattern(regexp = WebUtils.REGEX_HASH_PATTERN)
	private String hash;

	public String getHash()
	{
		return hash;
	}

	public void setHash(String hash)
	{
		this.hash = hash;
	}

	@Override
	public String toString()
	{
		return "HashCommand [hash=" + hash + "]";
	}

}
