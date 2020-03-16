package com.demo.security.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.security.auth.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	public Account findByEmail(String email);
	public Account findByUsername(String username);
}
