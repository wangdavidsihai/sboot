package com.cmi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author David Wang
 *
 */
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	@Qualifier("userDetailsServiceImpl")
	UserDetailsService userDetailsService;

	private static final String[] AUTH_WHITELIST = {
			// -- register url
			"/users/signup",
			// -- swagger ui
			"/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
			"/configuration/security", "/swagger-ui.html", "/webjars/**"
			// other public endpoints of your API may be appended to this array
	};

	// 该方法是登录的时候会进入
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 使用自定义身份验证组件
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
		// auth.authenticationProvider(new
		// CustomAuthenticationProvider(userDetailsService, BCryptPasswordEncoder));
	}

	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		// TODO Auto-generated method stub
		return super.userDetailsServiceBean();
	}

	@Override
	protected UserDetailsService userDetailsService() {
		// TODO Auto-generated method stub
		return super.userDetailsService();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		super.configure(web);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().disable();
		http.csrf().disable();
		http.authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll().anyRequest().authenticated().and()
				.addFilter(new CustomerAuthenticationFilter(authenticationManager()))
				.addFilter(new CustomerAuthenticationTokenFilter(authenticationManager()));
	}

}
