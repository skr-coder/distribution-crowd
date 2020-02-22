package com.wcan.scms.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignSuccessVO {
	
	private String username;
	private String email;
	private String token;

}