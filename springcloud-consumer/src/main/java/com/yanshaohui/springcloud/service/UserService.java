package com.yanshaohui.springcloud.service;

import java.util.List;

import com.yanshaohui.springcloud.entity.UserEntity;

public interface UserService {

	List<UserEntity> findAll();
}
