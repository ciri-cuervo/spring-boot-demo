package com.springdemo.domain.types;

public enum UserStatus {

	ACTIVE("A"),
	WAITING_ACTIVATION("W");

	private final String text;

	private UserStatus(final String text) {
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

	public static UserStatus valueForText(String text)
	{
		for (UserStatus value : values())
		{
			if (value.text.equals(text))
			{
				return value;
			}
		}
		return null;
	}

}
