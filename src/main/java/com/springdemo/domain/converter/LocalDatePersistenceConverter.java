package com.springdemo.domain.converter;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Converts from/to Java8 LocalDate and SQL Date
 */
@Converter(autoApply = true)
public class LocalDatePersistenceConverter implements AttributeConverter<LocalDate, Date> {

	@Override
	public Date convertToDatabaseColumn(LocalDate entityValue)
	{
		return entityValue == null ? null : Date.valueOf(entityValue);
	}

	@Override
	public LocalDate convertToEntityAttribute(Date databaseValue)
	{
		return databaseValue == null ? null : databaseValue.toLocalDate();
	}

}
