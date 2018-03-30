package com.yanshaohui.springcloud.controller;

import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rf")
@Produces("application/json; charset=UTF-8")
@RefreshScope
public class RefreshController {
	
	private static Logger logger = LoggerFactory.getLogger(RefreshController.class);

	@Value("${ysh.testValue}")
	private String configValue;
	
    @GetMapping("/b1")
    public String hello4() {
    	logger.debug("b1 be called");
    	return configValue;
    }
}
