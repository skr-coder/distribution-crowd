package com.wcan.scms.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wcan.scms.entity.ResultEntity;

@FeignClient(value="redis-provider")
public interface RedisOperationRemoteService {
	
	/**
	 * 将字符串类型的键值对保存到Redis时调用的远程方法
	 * @param normalKey
	 * @param normalValue
	 * @param timeoutMinute	超时时间（单位：分钟）
	 * 		-1表示无过期时间
	 * 		正数表示过期时间分钟数
	 * 		0和null值不接受
	 * @return
	 */
	@RequestMapping("/save/normal/string/key/value")
	ResultEntity<String> saveNormalStringKeyValue(@RequestParam("normalKey") String normalKey, @RequestParam("normalValue") String normalValue, @RequestParam("timeoutMinute") Integer timeoutMinute);
	
	/**
	 * 根据key查询对应value时调用的远程方法
	 * @param normalKey
	 * @return
	 */
	@RequestMapping("/retrieve/string/value/by/string/key")
	ResultEntity<String> retrieveStringValueByStringKey(@RequestParam("normalKey") String normalKey);
	
	/**
	 * 根据key删除对应value时调用的远程方法
	 * @param key
	 * @return
	 */
	@RequestMapping("/redis/remove/by/key")
	ResultEntity<String> removeByKey(@RequestParam("key") String key);
	
}