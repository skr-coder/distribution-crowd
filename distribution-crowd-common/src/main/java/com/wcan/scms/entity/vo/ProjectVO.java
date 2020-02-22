package com.wcan.scms.entity.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectVO {
	
	// 用户登录系统后，系统分配的token值，用于识别用户身份。
	// 用户的id可以根据token值查询Redis得到
	private String memberSignToken;

	// 在Redis中临时存储项目数据的token值
	private String projectTempToken;
	
	// 分类id集合
	private List<Integer> typeIdList;
	
	// 标签id集合
	private List<Integer> tagIdList;
	
	// 项目名称
	private String projectName;
	
	// 项目描述
	private String projectDescription;
	
	// 计划筹集的金额
	private Integer money;
	
	// 筹集资金的天数
	private Integer day;
	
	// 创建项目的日期
	private String createdate;
	
	// 头图的路径
	private String headerPicturePath;
	
	// 详情图片的路径
	private List<String> detailPicturePathList;
	
	// 发起人信息
	private MemberLauchInfoVO memberLauchInfoVO;
	
	// 回报信息集合
	private List<ReturnVO> returnVOList;
	
	// 发起人确认信息
	private MemberConfirmInfoVO memberConfirmInfoVO;
}