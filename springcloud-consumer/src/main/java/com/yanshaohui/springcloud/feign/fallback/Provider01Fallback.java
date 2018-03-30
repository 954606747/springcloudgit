package com.yanshaohui.springcloud.feign.fallback;

import org.springframework.stereotype.Component;

import com.yanshaohui.springcloud.feign.Provider01FeignClient;

@Component
public class Provider01Fallback implements Provider01FeignClient {

	@Override
	public String hello() {
		return "Feign发生错误被短路";
	}

	@Override
	public String excepton() {
		return "Feign发生服务端错误被短路";
	}

	@Override
	public String error() {
		return "Feign发生客户端错误被短路";
	}
}
