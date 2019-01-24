package com.cmi.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import com.cmi.util.JwtTokenUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * @author David Wang
 *
 */
public class CustomerAuthenticationTokenFilter extends BasicAuthenticationFilter {

	/**
	 * @param authenticationManager
	 * @param authenticationEntryPoint
	 */
	public CustomerAuthenticationTokenFilter(AuthenticationManager authenticationManager,
			AuthenticationEntryPoint authenticationEntryPoint) {
		super(authenticationManager, authenticationEntryPoint);
	}

	public CustomerAuthenticationTokenFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String authenToken = request.getHeader("Authorization");
		if (StringUtils.isEmpty(authenToken) || !authenToken.startsWith("Bearer ")) {
			chain.doFilter(request, response);
			return;
		}
		UsernamePasswordAuthenticationToken authentication = getAuthentication(authenToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

	/**
	 * @param authenToken
	 * @return
	 */
	private UsernamePasswordAuthenticationToken getAuthentication(String authenToken) {
		try {
			String user = JwtTokenUtils.getUsername(authenToken);
			String roles = JwtTokenUtils.getUserRole(authenToken);
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			if (authorities != null) {
				for (String role : roles.split("\\|")) {
					if (!StringUtils.isEmpty(role)) {
						SimpleGrantedAuthority grantedAuthor = new SimpleGrantedAuthority(role);
						authorities.add(grantedAuthor);
					}
				}
			}
			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, authorities);
			}
		} catch (ExpiredJwtException e) {
			logger.error("Token已过期: {} " + e);
		} catch (UnsupportedJwtException e) {
			logger.error("Token格式错误: {} " + e);
		} catch (MalformedJwtException e) {
			logger.error("Token没有被正确构造: {} " + e);
		} catch (SignatureException e) {
			logger.error("签名失败: {} " + e);
		} catch (IllegalArgumentException e) {
			logger.error("非法参数异常: {} " + e);
		}
		return null;
	}

}
