package com.cmi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmi.entity.LoginUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/users")
@Api(value = "用户管理", description = "用户管理")
public class UserController extends BaseController {

	/**
	 * 获取用户列表
	 * 
	 * @return
	 */
	@ApiOperation(value = "查询用户列表")
	@GetMapping("/userList")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Map<String, Object> userList() {
		List<LoginUser> users = userRepository.findAll();
		logger.info("users: {}", users);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("users", users);
		return map;
	}

	@ApiOperation(value = "查询用户权限")
	@GetMapping("/authorityList")
	@PreAuthorize("hasRole('ROLE_USER')")
	public List<String> authorityList() {
		List<String> authentication = getAuthentication();
		return authentication;
	}

}
