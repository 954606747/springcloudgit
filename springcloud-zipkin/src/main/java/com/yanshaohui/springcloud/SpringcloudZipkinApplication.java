package com.yanshaohui.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
@EnableDiscoveryClient
public class SpringcloudZipkinApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudZipkinApplication.class, args);
	}
}
