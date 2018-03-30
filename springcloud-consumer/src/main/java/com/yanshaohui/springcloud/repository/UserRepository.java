package com.yanshaohui.springcloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yanshaohui.springcloud.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Long>{
	UserEntity findByLoginAccount(String loginAccount);
}
