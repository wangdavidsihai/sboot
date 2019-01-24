package com.cmi.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sys_user")
public class LoginUser {

	@Id
	@GeneratedValue
	private long id;
	private String username;
	private String password;
	private int rememberMe;

	public long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(int rememberMe) {
		this.rememberMe = rememberMe;
	}

	public void setId(long id) {
		this.id = id;
	}

}
