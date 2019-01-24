package com.cmi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmi.entity.Role;

/**
 * @author David Wang
 *
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

	List<Role> findByName(String name);

	Role findById(long id);
}
