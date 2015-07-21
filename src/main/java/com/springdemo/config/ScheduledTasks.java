package com.springdemo.config;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.springdemo.domain.EmailHashRepository;
import com.springdemo.domain.UserRepository;

@Configuration
@EnableScheduling
public class ScheduledTasks {

	private static final Logger LOG = LoggerFactory.getLogger(ScheduledTasks.class);

	private static final long ONE_MINUTE = 60 * 1000;
	private static final long FIVE_MINUTES = 5 * ONE_MINUTE;
	private static final long ONE_HOUR = 60 * ONE_MINUTE;
	private static final long ONE_DAY = 24 * ONE_HOUR;

	@Autowired
	private Environment environment;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EmailHashRepository emailHashRepository;

	private long hashExpireDuration;

	@PostConstruct
	public void postConstruct()
	{
		hashExpireDuration = environment.getProperty("application.hash.expire.duration",
				Long.class, 2L);
	}

	/*
	 * This task deactivates expired email hashes
	 */
	@Scheduled(initialDelay = ONE_MINUTE, fixedRate = FIVE_MINUTES)
	public void deactivateExpiredEmailHash()
	{
		Integer emailHashDeactivated = emailHashRepository.deactivateByExpired();
		if (emailHashDeactivated > 0)
		{
			LOG.info("{} EmailHash were/was deactivated", emailHashDeactivated);
		}
	}

	/*
	 * This task removes inactive email hashes
	 */
	@Scheduled(initialDelay = FIVE_MINUTES, fixedRate = ONE_DAY)
	public void deleteInactiveEmailHash()
	{
		Integer emailHashDeleted = emailHashRepository.deleteByActiveFalse();
		if (emailHashDeleted > 0)
		{
			LOG.info("{} EmailHash were/was deleted", emailHashDeleted);
		}
	}

	/*
	 * This task removes inactive users (those that didn't activate the account on time)
	 */
	@Scheduled(initialDelay = FIVE_MINUTES, fixedRate = ONE_DAY)
	public void deleteInactiveUser()
	{
		LocalDateTime maxCreationTime = LocalDateTime.now().minusHours(hashExpireDuration);
		Integer userDeleted = userRepository.deleteByInactive(maxCreationTime);
		if (userDeleted > 0)
		{
			LOG.info("{} User were/was deleted", userDeleted);
		}
	}

}
