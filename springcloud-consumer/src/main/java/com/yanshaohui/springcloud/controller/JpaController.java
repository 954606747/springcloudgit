package com.yanshaohui.springcloud.controller;

import java.util.List;

import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yanshaohui.springcloud.entity.UserEntity;
import com.yanshaohui.springcloud.service.UserService;

@RestController
@RequestMapping("/jpa")
@Produces("application/json; charset=UTF-8")
@RefreshScope
public class JpaController {
	
	private static Logger logger = LoggerFactory.getLogger(JpaController.class);
	
	@Autowired
	private UserService userService;

    @GetMapping("/all")
//    @PreAuthorize("hasRole('ADMIN')") //todo:不知为何会通杀，暂时保留不做吧
    public List<UserEntity> findall() {
    	logger.debug("findall be call");
    	return userService.findAll();
    }
    
    @GetMapping("/user")
    public Object currentUser() {
    	return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
