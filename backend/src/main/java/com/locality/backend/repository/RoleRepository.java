package com.locality.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.locality.backend.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByRolename(String rolename);
	
	@Modifying
	@Query(value = "ALTER TABLE roles AUTO_INCREMENT = 1;", nativeQuery = true)
	void resetAutoIncrement();

}
