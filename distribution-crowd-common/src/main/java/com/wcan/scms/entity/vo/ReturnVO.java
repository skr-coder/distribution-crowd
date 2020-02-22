package com.wcan.scms.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReturnVO {
	
	// 用户登录系统后，系统分配的token值，用于识别用户身份。
	// 用户的id可以根据token值查询Redis得到
	private String memberSignToken;
	
	// 在Redis中临时存储项目数据的token值
	private String projectTempToken;
	
	// 回报类型：0 - 实物回报， 1 虚拟物品回报
	private Integer type;
	
	// 支持金额
	private Integer supportmoney;
	
	// 回报内容介绍
	private String content;
	
	// 总回报数量，0为不限制
	private Integer count;
	
	// 是否限制单笔购买数量，0表示不限购，1表示限购
	private Integer signalpurchase;
	
	// 如果单笔限购，那么具体的限购数量
	private Integer purchase;
	
	// 运费，“0”为包邮
	private Integer freight;
	
	// 是否开发票，0 - 不开发票， 1 - 开发票
	private Integer invoice;
	
	// 众筹结束后返还回报物品天数
	private Integer returndate;
	
	// 说明图片路径
	private String describPicPath;

}