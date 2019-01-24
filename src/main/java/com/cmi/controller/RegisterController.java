package com.cmi.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cmi.entity.LoginUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/users")
@Api(value = "注册管理", description = "注册管理")
public class RegisterController extends BaseController {

	/**
	 * 注册用户 默认开启白名单
	 * 
	 * @param user
	 * @throws Exception
	 */
	@ApiOperation(value = "注册用户")
	@PostMapping("/signup")
	public LoginUser signup(@RequestBody LoginUser user) throws Exception {
		LoginUser bizUser = userRepository.findByUsername(user.getUsername());
		if (null != bizUser) {
			throw new Exception("用户已经存在");
		}
		/*
		 * user.setPassword(DigestUtils.md5DigestAsHex((user.getPassword()).getBytes()))
		 * ;
		 */
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

}
