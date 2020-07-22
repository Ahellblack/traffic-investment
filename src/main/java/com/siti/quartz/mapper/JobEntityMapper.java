package com.siti.quartz.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.siti.quartz.po.JobEntity;

import java.util.List;

/**
 * Created by quyue1205 on 2019/1/22.
 */
@Mapper
public interface JobEntityMapper {

    @Select("select * from job_entity where id=#{id}")
    JobEntity selectByPrimaryKey(@Param("id") Integer id);

    @Update("update job_entity set work_status=#{workStatus} where id=#{id}")
    void updateJobEntityWorkStatusByKey(@Param("id") Integer id, @Param("workStatus") Integer workStatus);

    @Select("select * from job_entity")
    List<JobEntity> selectAll();
}
