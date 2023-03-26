package com.locality.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.locality.backend.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
	
	User findByEmail(String email);
	
	@Query("select u from User u where u.role.name = ?1")
	List<User> findByRole(String role);
	
}
