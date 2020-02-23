package com.wcan.scms.controller;

import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wcan.scms.api.DataBaseOperationRemoteService;
import com.wcan.scms.api.RedisOperationRemoteService;
import com.wcan.scms.entity.po.MemberPO;
import com.wcan.scms.entity.vo.MemberSignSuccessVO;
import com.wcan.scms.entity.vo.MemberVO;
import com.wcan.scms.entity.ResultEntity;
import com.wcan.scms.utils.CrowdConstant;
import com.wcan.scms.utils.CrowdUtils;

@RestController
public class MemberController {
	
	@Autowired
	private RedisOperationRemoteService redisRemoteService;
	
	@Autowired
	private DataBaseOperationRemoteService dataBaseOperationRemoteService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	// Spring会根据@Value注解中的表达式读取yml或properties配置文件给成员变量设置对应的值
	@Value("${crowd.short.message.appCode}")
	private String appcode;
	
	@RequestMapping("/member/manager/logout")
	public ResultEntity<String> logout(@RequestParam("token") String token) {
		
		return redisRemoteService.removeByKey(token);
	}
	
	@RequestMapping("/member/manager/login")
	public ResultEntity<MemberSignSuccessVO> login(
			@RequestParam("loginAcct") String loginAcct, 
			@RequestParam("userPswd") String userPswd) {
	
		// 1.根据登录账号查询数据库获取MemberPO对象
		ResultEntity<MemberPO> resultEntity = dataBaseOperationRemoteService.retrieveMemberByLoginAcct(loginAcct);
		
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return ResultEntity.failed(resultEntity.getMessage());
		}
		
		// 2.获取MemberPO对象
		MemberPO memberPO = resultEntity.getData();
		
		// 3.检查MemberPO对象是否为空
		if(memberPO == null) {
			return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_FAILED);
		}
		
		// 4.获取从数据库查询得到的密码
		String userpswdDatabase = memberPO.getUserpswd();
		
		// 5.比较密码
		boolean matcheResult = passwordEncoder.matches(userPswd, userpswdDatabase);
		
		if(!matcheResult) {
			return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_FAILED);
		}
		
		// 6.生成token
		String token = CrowdUtils.generateToken();
		
		// 7.从MemberPO对象获取memberId
		String memberId = memberPO.getId() + "";
		
		// 8.将token和memberId存入Redis
		ResultEntity<String> resultEntitySaveToken = redisRemoteService.saveNormalStringKeyValue(token, memberId, 30);
		
		if(ResultEntity.FAILED.equals(resultEntitySaveToken.getResult())) {
			return ResultEntity.failed(resultEntitySaveToken.getMessage());
		}
		
		// 9.封装MemberSignSuccessVO对象
		MemberSignSuccessVO memberSignSuccessVO = new MemberSignSuccessVO();
		
		BeanUtils.copyProperties(memberPO, memberSignSuccessVO);
		
		memberSignSuccessVO.setToken(token);
		
		// 10.返回结果
		return ResultEntity.successWithData(memberSignSuccessVO);
	}
	
	@RequestMapping("/member/manager/register")
	public ResultEntity<String> register(@RequestBody MemberVO memberVO) {
		
		// 1.检查验证码是否有效
		String randomCode = memberVO.getRandomCode();
		if(!CrowdUtils.strEffectiveCheck(randomCode)) {
			return ResultEntity.failed(CrowdConstant.MESSAGE_CODE_INVALID);
		}
		
		// 2.检查手机号是否有效
		String phoneNum = memberVO.getPhoneNum();
		if(!CrowdUtils.strEffectiveCheck(phoneNum)) {
			return ResultEntity.failed(CrowdConstant.MESSAGE_PHONE_NUM_INVALID);
		}
		
		// 3.拼接随机验证码的KEY
		String randomCodeKey = CrowdConstant.REDIS_RANDOM_CODE_PREFIX + phoneNum;
		
		// 4.远程调用redis-provider的方法获取对应的验证码值
		ResultEntity<String> resultEntity = redisRemoteService.retrieveStringValueByStringKey(randomCodeKey);
		
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return resultEntity;
		}
		
		String randomCodeRedis = resultEntity.getData();
		
		// 5.没有查询到值：返回失败信息，停止执行
		if(randomCodeRedis == null) {
			return ResultEntity.failed(CrowdConstant.MESSAGE_CODE_NOT_EXISTS);
		}
		
		// 6.进行比较
		if(!Objects.equals(randomCode, randomCodeRedis)) {
			return ResultEntity.failed(CrowdConstant.MESSAGE_CODE_NOT_MATCH);
		}
		
		// 7.从Redis中删除当前KEY对应的验证码
		ResultEntity<String> resultEntityRemoveByKey = redisRemoteService.removeByKey(randomCodeKey);
		
		if(ResultEntity.FAILED.equals(resultEntityRemoveByKey.getResult())) {
			return resultEntityRemoveByKey;
		}
		
		// 8.远程调用database-provider方法检查登录账号是否被占用
		String loginacct = memberVO.getLoginacct();
		ResultEntity<Integer> resultEntityRetrieveLoignAcctCount = dataBaseOperationRemoteService.retrieveLoignAcctCount(loginacct);
		
		if(ResultEntity.FAILED.equals(resultEntityRetrieveLoignAcctCount.getResult())) {
			return ResultEntity.failed(resultEntityRetrieveLoignAcctCount.getMessage());
		}
		
		Integer count = resultEntityRetrieveLoignAcctCount.getData();
		
		// 9.已经被占用：返回失败信息，停止执行
		if(count > 0) {
			return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
		}
		
		// 10.密码加密
		String userpswd = memberVO.getUserpswd();
		String userpswdEncoded = passwordEncoder.encode(userpswd);
		memberVO.setUserpswd(userpswdEncoded);
		
		// 11.远程调用database-provider方法执行保存操作
		
		MemberPO memberPO = new MemberPO();
		
		// 调用Spring提供的BeanUtils.copyProperties()工具类直接完成属性值注入
		BeanUtils.copyProperties(memberVO, memberPO);
		
		return dataBaseOperationRemoteService.saveMemberRemote(memberPO);
	}
	
	@RequestMapping("/member/manager/send/code")
	public ResultEntity<String> sendCode(@RequestParam("phoneNum") String phoneNum) {
		
		if(!CrowdUtils.strEffectiveCheck(phoneNum)) {
			return ResultEntity.failed(CrowdConstant.MESSAGE_PHONE_NUM_INVALID);
		}
		
		// 1.生成验证码
		int length = 4;
		String randomCode = CrowdUtils.randomCode(length);
		
		// 2.保存到Redis
		Integer timeoutMinute = 15;
		
		String normalKey = CrowdConstant.REDIS_RANDOM_CODE_PREFIX + phoneNum;
		
		ResultEntity<String> resultEntity = redisRemoteService.saveNormalStringKeyValue(normalKey, randomCode, timeoutMinute);
		
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			return resultEntity;
		}
		
		// 3.发送验证码到用户手机
		
		try {
			CrowdUtils.sendShortMessage(appcode, randomCode, phoneNum);
			
			return ResultEntity.successNoData();
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResultEntity.failed(e.getMessage());
		}
		
	}
	
}
