package com.wcan.scms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wcan.scms.entity.ResultEntity;
import com.wcan.scms.entity.vo.ProjectVO;
import com.wcan.scms.service.ProjectService;

@RestController
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping("/save/project/remote/{memberId}")
	public ResultEntity<String> saveProjectRemote(
			@RequestBody ProjectVO projectVO, 
			@PathVariable("memberId") String memberId) {
		
		try {
			projectService.saveProject(projectVO, memberId);
			
			return ResultEntity.successNoData();
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResultEntity.failed(e.getMessage());
		}
		
	}

}
