package com.locality.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.locality.backend.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

User findByUsername(String username);
	
	User findByEmail(String email);
	
	@Modifying
	@Query(value = "ALTER TABLE users AUTO_INCREMENT = 1;", nativeQuery = true)
	void resetAutoIncrement();
}