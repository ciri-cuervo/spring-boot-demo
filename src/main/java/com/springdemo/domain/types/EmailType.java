package com.springdemo.domain.types;

public enum EmailType {

	ACTIVATION("A"),
	RESTORE("R");

	private final String text;

	private EmailType(final String text) {
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

	public static EmailType valueForText(String text)
	{
		for (EmailType value : values())
		{
			if (value.text.equals(text))
			{
				return value;
			}
		}
		return null;
	}

}
