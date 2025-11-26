package com.zzyl.mapper;

import com.github.pagehelper.Page;
import com.zzyl.entity.NursingProject;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 护理项目Mapper接口
 */
@Mapper
public interface NursingProjectMapper {

    Page<NursingProject> selectByPage(String name, Integer status);

    void insert(NursingProject nursingProject);
    //根据id查询护理项目
    NursingProject selectById(Long id);
    //修改护理项目
    void update(NursingProject nursingProject);
    //删除护理项目
    void deleteById(Long id);

    /**
     * 根据状态查询护理项目列表
     * @param status 状态
     * @return 护理项目列表
     */
    List<NursingProject> listByStatus(Integer status);
}