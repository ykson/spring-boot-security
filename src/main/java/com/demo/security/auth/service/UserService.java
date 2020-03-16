package com.demo.security.auth.service;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.security.auth.eo.ERole;
import com.demo.security.auth.model.Account;
import com.demo.security.auth.model.Role;
import com.demo.security.auth.repository.RoleRepository;
import com.demo.security.auth.repository.AccountRepository;

@Service
public class UserService {
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	public Account findUserByEmail(String email) {
		return accountRepository.findByEmail(email);
	}
	
	public Account findUserByUserName(String username) {
		return accountRepository.findByUsername(username);
	}
	
	public Account saveUser(Account user) {
		user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
		user.setIsActive(true);
		Role userRole = roleRepository.findByRole(ERole.ADMIN.getValue());
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		
		return accountRepository.save(user);
	}
}
