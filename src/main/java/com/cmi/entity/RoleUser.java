package com.cmi.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author David Wang
 *
 */
@Entity
@Table(name = "sys_role_user")
public class RoleUser {

	@Id
	@GeneratedValue
	private long id;
	private long sysUserId;
	private long sysRoleId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(long sysUserId) {
		this.sysUserId = sysUserId;
	}

	public long getSysRoleId() {
		return sysRoleId;
	}

	public void setSysRoleId(long sysRoleId) {
		this.sysRoleId = sysRoleId;
	}

}
