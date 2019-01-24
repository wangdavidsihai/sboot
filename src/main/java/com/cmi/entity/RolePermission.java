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
@Table(name = "sys_permission_role")
public class RolePermission {

	@Id
	@GeneratedValue
	private long id;
	private long sysPermissionId;
	private long sysRoleId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSysPermissionId() {
		return sysPermissionId;
	}

	public void setSysPermissionId(long sysPermissionId) {
		this.sysPermissionId = sysPermissionId;
	}

	public long getSysRoleId() {
		return sysRoleId;
	}

	public void setSysRoleId(long sysRoleId) {
		this.sysRoleId = sysRoleId;
	}

}
