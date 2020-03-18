package com.demo.security.auth.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.security.auth.AccountService;
import com.demo.security.auth.model.Account;
import com.demo.security.auth.model.Role;

@Service(value = "customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	@Resource(name = "accountServiceImpl")
	private AccountService accountService;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//< get the user information
		Account user = null;
		try {
			user = accountService.getUserByUsername(username);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new UsernameNotFoundException(e.getMessage());
		}
		
		//< set the user authorities
		List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
		
		//< return the user details
		return buildUserForAuthentication(user, authorities);
	}
	
	private List<GrantedAuthority> getUserAuthority(Set<Role> userRole) {
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		for(Role role : userRole) {
			roles.add(new SimpleGrantedAuthority(role.getRole()));
		}
		
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(roles);
		
		return grantedAuthorities;
	}
	
	private UserDetails buildUserForAuthentication(Account user, List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getPassword(), user.getIsActive(), true, true, true, authorities);
	}
}






