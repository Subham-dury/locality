package com.locality.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.locality.backend.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
	
	User findByEmail(String email);
	
	void deleteByUsername(String username);

}
