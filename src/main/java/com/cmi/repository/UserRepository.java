package com.cmi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cmi.entity.LoginUser;

/**
 * @author David Wang
 *
 */
public interface UserRepository extends JpaRepository<LoginUser, Long> {

	LoginUser findByUsername(String username);

}
