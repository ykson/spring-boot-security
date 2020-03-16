package com.demo.security.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.security.auth.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	public Role findByRole(String role);
}
