package com.yanshaohui.springcloud.controller;

import java.util.Random;

import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yanshaohui.springcloud.entity.UserEntity;
import com.yanshaohui.springcloud.stream.SendService;

@RestController
@RequestMapping("/stream")
@Produces("application/json; charset=UTF-8")
@RefreshScope
public class StreamController {
	
	private static Logger logger = LoggerFactory.getLogger(StreamController.class);
	
	@Autowired
	private SendService sendService;

    @GetMapping("/c1/{name}")
    public String hello5(@PathVariable("name") String name) {
    	logger.debug("c1 be called('{}')",name);
    	
    	UserEntity user = new UserEntity();
    	user.setUserName(name);
    	user.setAge(new Random().nextInt(130));
    	user.setSex(new Random().nextBoolean()?"男":"女");
    	user.setAddress("陕西省西安市科技二路软件园零壹广场");
    	logger.debug("the user entity output: '{}'",user);
    	
    	sendService.sendMessage(user);
    	return "send complete";
    }
}
