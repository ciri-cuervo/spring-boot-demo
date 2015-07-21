package com.springdemo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.springframework.security.core.GrantedAuthority;

import com.springdemo.domain.types.RoleAuthority;

@Entity
public class Role extends AbstractGeneratedIdEntity<Long> implements GrantedAuthority {
	private static final long serialVersionUID = 5338490775191847740L;

	@Column(nullable = false, unique = true)
	private String authority;

	private String description;

	public Role() {
	}

	public Role(RoleAuthority authority, String description) {
		this.authority = authority.toString();
		this.description = description;
	}

	@Override
	public String getAuthority()
	{
		return authority;
	}

	public void setAuthority(String authority)
	{
		this.authority = authority;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@Override
	public String toString()
	{
		return "Role [authority=" + authority + ", description=" + description + ", "
				+ super.toString() + "]";
	}

}
