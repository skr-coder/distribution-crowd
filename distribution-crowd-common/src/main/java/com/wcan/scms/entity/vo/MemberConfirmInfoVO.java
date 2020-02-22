package com.wcan.scms.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberConfirmInfoVO {
	
	// 用户登录系统后，系统分配的token值，用于识别用户身份。
	// 用户的id可以根据token值查询Redis得到
	private String memberSignToken;

	// 在Redis中临时存储项目数据的token值
	private String projectTempToken;
	
	// 易付宝账号
	private String paynum;

	// 法人身份证号
	private String cardnum;
}