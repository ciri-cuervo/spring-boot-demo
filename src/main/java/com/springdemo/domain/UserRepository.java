package com.springdemo.domain;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

	User findByEmail(String email);

	@Transactional
	@Modifying(clearAutomatically = true)
	// TODO change 1 for UserStatus
	@Query("delete User u where u.status = 1 and u.createdDate <= ?1")
	Integer deleteByInactive(LocalDateTime createdDate);

}
