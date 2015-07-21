package com.springdemo.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import com.springdemo.domain.converter.LocalDatePersistenceConverter;
import com.springdemo.domain.types.UserStatus;

@Entity
public class User extends AbstractGeneratedIdEntity<Long> implements UserDetails {
	private static final long serialVersionUID = 2601721411763278400L;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, unique = true)
	private String email;

	private String firtname;

	private String lastname;

	private String address;

	private String phone;

	private String info;

	@Convert(converter = LocalDatePersistenceConverter.class)
	private LocalDate dob;

	@Column(nullable = false)
	private UserStatus status;

	@ManyToMany
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") }, uniqueConstraints = { @UniqueConstraint(columnNames = {
			"user_id", "role_id" }) })
	private List<Role> authorities = new ArrayList<>();

	public User() {
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	@Override
	public boolean isEnabled()
	{
		return false;
	}

	/*
	 * GETTERS AND SETTERS
	 */

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getFirtname()
	{
		return firtname;
	}

	public void setFirtname(String firtname)
	{
		this.firtname = firtname;
	}

	public String getLastname()
	{
		return lastname;
	}

	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getInfo()
	{
		return info;
	}

	public void setInfo(String info)
	{
		this.info = info;
	}

	public LocalDate getDob()
	{
		return dob;
	}

	public void setDob(LocalDate dob)
	{
		this.dob = dob;
	}

	public UserStatus getStatus()
	{
		return status;
	}

	public void setStatus(UserStatus status)
	{
		this.status = status;
	}

	@Override
	public List<Role> getAuthorities()
	{
		return authorities;
	}

	public void setAuthorities(List<Role> authorities)
	{
		this.authorities = authorities;
	}

	public boolean addAuthority(Role authority)
	{
		Assert.notNull(authority);

		List<Role> authorities = new ArrayList<>(getAuthorities());
		if (authorities.add(authority))
		{
			setAuthorities(authorities);
			return true;
		}
		return false;
	}

	public boolean removeAuthority(Role authority)
	{
		Assert.notNull(authority);

		List<Role> authorities = new ArrayList<>(getAuthorities());
		if (authorities.remove(authority))
		{
			setAuthorities(authorities);
			return true;
		}
		return false;
	}

	@Override
	public String toString()
	{
		return "User [username=" + username + ", password=" + password + ", email=" + email
				+ ", firtname=" + firtname + ", lastname=" + lastname + ", address=" + address
				+ ", phone=" + phone + ", info=" + info + ", dob=" + dob + ", status=" + status
				+ ", authorities=" + authorities + ", " + super.toString() + "]";
	}

}
