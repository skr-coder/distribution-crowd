package com.wcan.scms.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedis {
	
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Test
	public void testSaveAndGetValueToRedisByRedisTemplate() {
		// 获取Redis操作器
		ValueOperations<Object, Object> operator = redisTemplate.opsForValue();
		
		// 设置值
		// operator.set("keyone", "valueone");
		
		// 获取值
		Object value = operator.get("keyone");
		System.out.println(value);
	}
	
	@Test
	public void testSaveAndGetValueToRedisByStringRedisTemplate() {
		
		// 获取Redis操作器
		ValueOperations<String, String> operator = stringRedisTemplate.opsForValue();
		
		// 设置值
		 operator.set("keytwo", "valuetwo");
		
		// 获取值
		String value = operator.get("keytwo");
		System.out.println(value);
	}
}

