package com.wcan.scms.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {
	
	private String loginacct;
	
	private String userpswd;
	
	private String phoneNum;
	
	private String randomCode;

}