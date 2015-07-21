package com.springdemo.domain.types;

public enum RoleAuthority {

	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER");

	private final String text;

	private RoleAuthority(final String text) {
		this.text = text;
	}

	public boolean equalsText(String text)
	{
		return (text == null) ? false : this.text.equals(text);
	}

	@Override
	public String toString()
	{
		return text;
	}

	public static RoleAuthority valueForText(String text)
	{
		for (RoleAuthority value : values())
		{
			if (value.text.equals(text))
			{
				return value;
			}
		}
		return null;
	}

}
