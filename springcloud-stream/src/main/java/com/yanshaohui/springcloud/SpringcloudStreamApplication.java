package com.yanshaohui.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringcloudStreamApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudStreamApplication.class, args);
	}
}
