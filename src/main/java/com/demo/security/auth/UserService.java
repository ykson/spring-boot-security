package com.demo.security.auth;

import com.demo.security.auth.model.Account;

public interface UserService {
	public Account getUserByEmail(String email) throws Exception;
	public Account getUserByUsername(String username) throws Exception;
	public Account setUser(Account user) throws Exception;
}
