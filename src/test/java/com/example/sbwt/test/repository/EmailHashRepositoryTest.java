package com.example.sbwt.test.repository;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.sbwt.test.config.TestConfiguration;
import com.springdemo.domain.EmailHash;
import com.springdemo.domain.EmailHashRepository;
import com.springdemo.domain.types.EmailType;
import com.springdemo.util.RandomStringUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestConfiguration.class)
@Transactional
public class EmailHashRepositoryTest {

	private static final LocalDateTime NOW = LocalDateTime.now();
	private static final LocalDateTime TOMORROW = NOW.plusDays(1);

	@Autowired
	private EmailHashRepository emailHashRepository;

	private EmailHash emailHash;
	private EmailHash emailHashExpired;

	@Before
	public void setUp() throws Exception {
		emailHash = new EmailHash();
		emailHash.setEmail("testuser@email.com");
		emailHash.setHash(RandomStringUtils.randomAlphanumeric(64));
		emailHash.setType(EmailType.ACTIVATION);
		emailHash.setActive(true);
		emailHash.setExpire(TOMORROW);

		emailHashExpired = new EmailHash();
		emailHashExpired.setEmail("testuser@email.com");
		emailHashExpired.setHash(RandomStringUtils.randomAlphanumeric(64));
		emailHashExpired.setType(EmailType.ACTIVATION);
		emailHashExpired.setActive(true);
		emailHashExpired.setExpire(NOW);
	}

	@Test
	public void testCreateEmailHash() {
		emailHash = emailHashRepository.save(emailHash);
		EmailHash actual = emailHashRepository.findByHash(emailHash.getHash());

		assertNotNull(actual);
		assertEquals(emailHash.getId(), actual.getId());
		assertEquals(emailHash.getEmail(), actual.getEmail());
		assertEquals(emailHash.getHash(), actual.getHash());
		assertNotNull(actual.getCreatedDate());
		assertNotNull(actual.getLastModifiedDate());
	}

	@Test
	public void testFindByHashAndActiveTrue() {
		emailHash = emailHashRepository.save(emailHash);
		emailHashExpired = emailHashRepository.save(emailHashExpired);

		EmailHash actual = emailHashRepository.findByHashAndActiveTrue(emailHash.getHash());
		assertNotNull(actual);
		assertEquals(emailHash, actual);

		actual = emailHashRepository.findByHashAndActiveTrue(emailHashExpired.getHash());
		assertNotNull(actual);
		assertEquals(emailHashExpired, actual);

		emailHash.setActive(false);
		emailHash = emailHashRepository.save(emailHash);

		actual = emailHashRepository.findByHashAndActiveTrue(emailHash.getHash());
		assertNull(actual);
	}

	@Test
	public void testFindByEmailAndTypeAndActiveTrue() {
		emailHash = emailHashRepository.save(emailHash);
		emailHashExpired.setActive(false);
		emailHashExpired = emailHashRepository.save(emailHashExpired);

		EmailHash actual = emailHashRepository.findByEmailAndTypeAndActiveTrue(emailHash.getEmail(), EmailType.ACTIVATION);
		assertNotNull(actual);
		assertEquals(emailHash, actual);

		emailHash.setActive(false);
		emailHash = emailHashRepository.save(emailHash);

		actual = emailHashRepository.findByEmailAndTypeAndActiveTrue(emailHash.getEmail(), EmailType.ACTIVATION);
		assertNull(actual);
	}

	@Test
	public void testDeactivateByEmail() {
		emailHash = emailHashRepository.save(emailHash);
		emailHashExpired = emailHashRepository.save(emailHashExpired);

		assertTrue(emailHash.getActive());
		assertTrue(emailHashExpired.getActive());

		Integer rows = emailHashRepository.deactivateByEmail(emailHash.getEmail());
		assertEquals(Integer.valueOf(2), rows);

		EmailHash actual = emailHashRepository.findByHash(emailHash.getHash());
		assertNotNull(actual);
		assertEquals(emailHash, actual);
		assertFalse(actual.getActive());

		actual = emailHashRepository.findByHash(emailHashExpired.getHash());
		assertNotNull(actual);
		assertEquals(emailHashExpired, actual);
		assertFalse(actual.getActive());
	}

	@Test
	public void testDeactivateByExpired() {
		emailHash = emailHashRepository.save(emailHash);
		emailHashExpired = emailHashRepository.save(emailHashExpired);

		assertTrue(emailHash.getActive());
		assertTrue(emailHashExpired.getActive());

		Integer rows = emailHashRepository.deactivateByExpired();
		assertEquals(Integer.valueOf(1), rows);

		EmailHash actual = emailHashRepository.findByHash(emailHash.getHash());
		assertNotNull(actual);
		assertEquals(emailHash, actual);
		assertTrue(actual.getActive());

		actual = emailHashRepository.findByHash(emailHashExpired.getHash());
		assertNotNull(actual);
		assertEquals(emailHashExpired, actual);
		assertFalse(actual.getActive());
	}

	@Test
	public void testDeleteByActiveFalse() {
		emailHash.setActive(false);
		emailHash = emailHashRepository.save(emailHash);
		Integer rows = emailHashRepository.deleteByActiveFalse();
		EmailHash actual = emailHashRepository.findByHash(emailHash.getHash());

		assertNull(actual);
		assertEquals(Integer.valueOf(1), rows);
	}

}
