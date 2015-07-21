package com.springdemo.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.springdemo.domain.converter.LocalDateTimePersistenceConverter;

/**
 * Superclass to derive entity classes from.
 * 
 * @param <K>
 *            the primary-key class
 */
@MappedSuperclass
public abstract class AbstractEmbeddedIdEntity<K extends Serializable> {

	@EmbeddedId
	protected K id;

	@Column(nullable = false, updatable = false)
	@Convert(converter = LocalDateTimePersistenceConverter.class)
	protected LocalDateTime createdDate;

	@Column(nullable = false)
	@Convert(converter = LocalDateTimePersistenceConverter.class)
	protected LocalDateTime lastModifiedDate;

	/**
	 * The default constructor.
	 */
	public AbstractEmbeddedIdEntity() {
	}

	@PrePersist
	public void onCreate()
	{
		this.createdDate = LocalDateTime.now();
		this.lastModifiedDate = this.createdDate;
	}

	@PreUpdate
	public void onUpdate()
	{
		this.lastModifiedDate = LocalDateTime.now();
	}

	/**
	 * The embedded id constructor.
	 * 
	 * @param id
	 */
	public AbstractEmbeddedIdEntity(K id) {
		this.id = id;
	}

	/**
	 * Returns the identifier of the entity.
	 * 
	 * @return the id
	 */
	public K getId()
	{
		return id;
	}

	/**
	 * Sets the identifier of the entity.
	 * 
	 * @param id
	 *            the identifier
	 */
	public void setId(K id)
	{
		this.id = id;
	}

	/**
	 * @return the createdDate
	 */
	public LocalDateTime getCreatedDate()
	{
		return createdDate;
	}

	/**
	 * @param createdDate
	 *            the createdDate to set
	 */
	public void setCreatedDate(LocalDateTime createdDate)
	{
		this.createdDate = createdDate;
	}

	/**
	 * @return the lastModifiedDate
	 */
	public LocalDateTime getLastModifiedDate()
	{
		return lastModifiedDate;
	}

	/**
	 * @param lastModifiedDate
	 *            the lastModifiedDate to set
	 */
	public void setLastModifiedDate(LocalDateTime lastModifiedDate)
	{
		this.lastModifiedDate = lastModifiedDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (id == null || obj == null || getClass() != obj.getClass())
			return false;
		AbstractEmbeddedIdEntity<?> that = (AbstractEmbeddedIdEntity<?>) obj;
		return id.equals(that.id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return id == null ? 0 : id.hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "id=" + id + ", createdDate=" + createdDate + ", lastModifiedDate="
				+ lastModifiedDate;
	}

}
