package com.springdemo.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.springdemo.domain.types.EmailType;

@Transactional(readOnly = true)
public interface EmailHashRepository extends JpaRepository<EmailHash, Long> {

	EmailHash findByHash(String hash);

	EmailHash findByHashAndActiveTrue(String hash);

	EmailHash findByEmailAndTypeAndActiveTrue(String email, EmailType type);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update EmailHash e set e.active = false where e.active = true and e.email = ?1")
	Integer deactivateByEmail(String email);

	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("update EmailHash e set e.active = false where e.active = true and e.expire <= CURRENT_TIMESTAMP")
	Integer deactivateByExpired();

	@Transactional
	Integer deleteByActiveFalse();

}
