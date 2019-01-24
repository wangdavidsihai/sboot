package com.cmi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmi.entity.Permission;

/**
 * @author David Wang
 *
 */
public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
