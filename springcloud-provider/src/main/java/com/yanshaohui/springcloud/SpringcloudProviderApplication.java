package com.yanshaohui.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//@SpringCloudApplication
@SpringBootApplication
@EnableDiscoveryClient
public class SpringcloudProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudProviderApplication.class, args);
	}
}
