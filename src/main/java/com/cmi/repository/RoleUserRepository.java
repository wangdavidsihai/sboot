package com.cmi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmi.entity.RoleUser;

/**
 * @author David Wang
 *
 */
public interface RoleUserRepository extends JpaRepository<RoleUser, Long> {

	List<RoleUser> findBySysUserId(long sysUserId);

	RoleUser findById(long id);
}
