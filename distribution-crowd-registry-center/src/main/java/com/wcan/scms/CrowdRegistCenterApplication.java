package com.wcan.scms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class CrowdRegistCenterApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CrowdRegistCenterApplication.class, args);
	}

}