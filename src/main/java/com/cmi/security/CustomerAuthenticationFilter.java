package com.cmi.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cmi.entity.LoginUser;
import com.cmi.util.JwtTokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author David Wang
 *
 */
public class CustomerAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private ThreadLocal<Integer> rememberMe = new ThreadLocal<Integer>();

	private AuthenticationManager authenticationManager;

	public CustomerAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		LoginUser user = null;
		try {
			user = new ObjectMapper().readValue(request.getInputStream(), LoginUser.class);
			rememberMe.set(user.getRememberMe());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), new ArrayList()));

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		User userDetails = (User) auth.getPrincipal();

		StringBuffer roles = new StringBuffer("");
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		for (GrantedAuthority authority : authorities) {
			roles.append(authority.getAuthority()).append("|");
		}

		boolean isRememberMe = rememberMe.get() == 1;
		String token = JwtTokenUtils.createToken(userDetails.getUsername(), roles.toString(), isRememberMe);
		res.addHeader("Authorization", "Bearer " + token);
	}
}
