package com.springdemo.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.springdemo.domain.converter.LocalDateTimePersistenceConverter;
import com.springdemo.domain.types.EmailType;

@Entity
@Table(indexes = { @Index(columnList = "email"), @Index(columnList = "active"),
		@Index(columnList = "expire") })
public class EmailHash extends AbstractGeneratedIdEntity<Long> {

	@Column(nullable = false, updatable = false)
	private String email;

	@Column(nullable = false, updatable = false, unique = true)
	private String hash;

	@Column(nullable = false, updatable = false)
	private EmailType type;

	@Column(nullable = false)
	private Boolean active;

	@Column(nullable = false, updatable = false)
	@Convert(converter = LocalDateTimePersistenceConverter.class)
	private LocalDateTime expire;

	public EmailHash() {
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getHash()
	{
		return hash;
	}

	public void setHash(String hash)
	{
		this.hash = hash;
	}

	public EmailType getType()
	{
		return type;
	}

	public void setType(EmailType type)
	{
		this.type = type;
	}

	public Boolean getActive()
	{
		return active;
	}

	public void setActive(Boolean active)
	{
		this.active = active;
	}

	public LocalDateTime getExpire()
	{
		return expire;
	}

	public void setExpire(LocalDateTime expire)
	{
		this.expire = expire;
	}

	@Override
	public String toString()
	{
		return "EmailHash [email=" + email + ", hash=" + hash + ", type=" + type + ", active="
				+ active + ", expire=" + expire + ", " + super.toString() + "]";
	}

}
