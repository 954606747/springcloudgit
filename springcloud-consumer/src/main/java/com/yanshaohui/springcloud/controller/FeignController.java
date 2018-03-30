package com.yanshaohui.springcloud.controller;

import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yanshaohui.springcloud.feign.Provider01FeignClient;

@RestController
@RequestMapping("/feign")
@Produces("application/json; charset=UTF-8")
@RefreshScope
public class FeignController {
	
	private static Logger logger = LoggerFactory.getLogger(FeignController.class);
	
	@Autowired
	private Provider01FeignClient provider01FeignClient;

    @GetMapping("/a1")
    public String hello() {
    	logger.debug("a1 be called");
    	return provider01FeignClient.hello();
    }
    
    @GetMapping("/a2")
    public String hello2() {
    	logger.debug("a2 be called");
    	return provider01FeignClient.excepton();
    }
    
    @GetMapping("/a3")
    public String hello3() {
    	logger.debug("a3 be called");
    	return provider01FeignClient.error();
    }
}
