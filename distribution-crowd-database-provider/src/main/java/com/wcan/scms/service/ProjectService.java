package com.wcan.scms.service;

import com.wcan.scms.entity.vo.ProjectVO;

public interface ProjectService {

	void saveProject(ProjectVO projectVO, String memberId);

}
