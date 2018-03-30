package com.yanshaohui.springcloud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/provider01")
//@Produces("application/json; charset=UTF-8")
public class MyProviderController {

	private static Logger logger = LoggerFactory.getLogger(MyProviderController.class);
	
    @Autowired
    private DiscoveryClient client;

    @GetMapping("/a1")
    public String hello() {
    	logger.debug("a1 be called.");
        ServiceInstance localInstance = client.getLocalServiceInstance();
        return "Service Provider: "+ localInstance.getServiceId()+":"+localInstance.getHost()+":"+localInstance.getPort();
    }
    
    @GetMapping("/a2")
    public String excepton() {
    	logger.debug("a2 be called.");
        throw new RuntimeException("服务端发生异常");
    }
    
    @GetMapping("/a3")
    @ResponseStatus(HttpStatus.BAD_REQUEST) 
    public String error() {
    	logger.debug("a3 be called.");
    	return "error";
    }

}
