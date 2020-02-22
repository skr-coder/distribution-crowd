package com.wcan.scms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient // 更为通用
@SpringBootApplication
public class CrowdRedisProviderApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CrowdRedisProviderApplication.class, args);
	}

}