package com.wcan.scms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient // 更为通用
@MapperScan("com.wcan.scms.mapper")	// 配置MyBatis的Mapper接口所在包进行自动扫描，配置了这个注解才能够将Mapper接口类型的bean注入Service。另外一种扫描Mapper的方法是在具体Mapper接口上使用@Mapper
@SpringBootApplication
public class CrowdDateProviderApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CrowdDateProviderApplication.class, args);
	}

}