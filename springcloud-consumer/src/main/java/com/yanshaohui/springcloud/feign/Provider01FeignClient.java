package com.yanshaohui.springcloud.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yanshaohui.springcloud.feign.fallback.Provider01Fallback;

@FeignClient(value="PROVIDER01",fallback = Provider01Fallback.class)
public interface Provider01FeignClient {
	
	@RequestMapping(value="/provider01/a1",method=RequestMethod.GET)
	String hello();
	
	@RequestMapping(value="/provider01/a2",method=RequestMethod.GET)
    String excepton();
    
    @RequestMapping(value="/provider01/a3",method=RequestMethod.GET)
    String error();
}
