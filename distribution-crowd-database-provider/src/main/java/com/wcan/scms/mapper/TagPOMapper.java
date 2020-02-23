package com.wcan.scms.mapper;

import com.wcan.scms.entity.po.TagPO;
import com.wcan.scms.entity.po.TagPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TagPOMapper {
    int countByExample(TagPOExample example);

    int deleteByExample(TagPOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TagPO record);

    int insertSelective(TagPO record);

    List<TagPO> selectByExample(TagPOExample example);

    TagPO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TagPO record, @Param("example") TagPOExample example);

    int updateByExample(@Param("record") TagPO record, @Param("example") TagPOExample example);

    int updateByPrimaryKeySelective(TagPO record);

    int updateByPrimaryKey(TagPO record);

    void insertRelationshipBatch(
			@Param("projectId") Integer projectId, 
			@Param("tagIdList") List<Integer> tagIdList);
}