package com.springdemo.domain.converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Converts from/to Java8 LocalDateTime and SQL Timestamp
 */
@Converter(autoApply = true)
public class LocalDateTimePersistenceConverter implements
		AttributeConverter<LocalDateTime, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(LocalDateTime entityValue)
	{
		return entityValue == null ? null : Timestamp.valueOf(entityValue);
	}

	@Override
	public LocalDateTime convertToEntityAttribute(Timestamp databaseValue)
	{
		return databaseValue == null ? null : databaseValue.toLocalDateTime();
	}

}
