package com.wcan.scms.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wcan.scms.entity.po.MemberPO;
import com.wcan.scms.entity.ResultEntity;

@FeignClient("database-provider")
public interface DataBaseOperationRemoteService {
	
	@RequestMapping("/retrieve/loign/acct/count")
	ResultEntity<Integer> retrieveLoignAcctCount(@RequestParam("loginAcct") String loginAcct);

	@RequestMapping("/save/member/remote")
	ResultEntity<String> saveMemberRemote(@RequestBody MemberPO memberPO);
	
	@RequestMapping("/retrieve/member/by/login/acct")
	ResultEntity<MemberPO> retrieveMemberByLoginAcct(@RequestParam("loginAcct") String loginAcct);
}