package com.wcan.scms.service;

import com.wcan.scms.entity.po.MemberPO;

public interface MemberService {

	MemberPO getMemberByLoginAcct(String loginAcct);

	int getLoginAcctCount(String loginAcct);

	void saveMemberPO(MemberPO memberPO);

}
