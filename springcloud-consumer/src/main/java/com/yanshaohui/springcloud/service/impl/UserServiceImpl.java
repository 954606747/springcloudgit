package com.yanshaohui.springcloud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yanshaohui.springcloud.entity.UserEntity;
import com.yanshaohui.springcloud.repository.UserRepository;
import com.yanshaohui.springcloud.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<UserEntity> findAll(){
		return userRepository.findAll();
	}
	
}
