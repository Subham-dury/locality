package com.locality.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.locality.backend.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByName(String string);

}
