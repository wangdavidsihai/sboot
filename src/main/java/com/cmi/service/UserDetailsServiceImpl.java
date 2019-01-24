package com.cmi.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cmi.entity.Role;
import com.cmi.entity.RoleUser;
import com.cmi.entity.LoginUser;
import com.cmi.repository.RoleRepository;
import com.cmi.repository.RoleUserRepository;
import com.cmi.repository.UserRepository;

/**
 * @author David Wang
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private RoleUserRepository roleUserRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#
	 * loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		LoginUser user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				grantedAuthorities(user.getId()));
	}

	/**
	 * @param id
	 * @return
	 */
	private Collection<? extends GrantedAuthority> grantedAuthorities(long userId) {
		List<RoleUser> roleUserList = roleUserRepository.findBySysUserId(userId);
		List<Role> roles = new ArrayList<Role>();

		for (RoleUser roleUser : roleUserList) {
			roles.add(roleRepository.findById(roleUser.getSysRoleId()));
		}
		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for (Role r : roles) {
			authorities.add(new SimpleGrantedAuthority(r.getName()));
		}
		return authorities;
	}

}
