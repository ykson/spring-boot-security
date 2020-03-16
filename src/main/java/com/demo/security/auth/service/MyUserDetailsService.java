package com.demo.security.auth.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.security.auth.model.Account;
import com.demo.security.auth.model.Role;

@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private UserService userService;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//< get the user information
		Account user = userService.findUserByUserName(username);
		List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
		
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
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getIsActive(), true, true, true, authorities);
	}
}






