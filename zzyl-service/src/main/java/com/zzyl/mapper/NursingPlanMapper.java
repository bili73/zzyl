package com.zzyl.mapper;

import com.github.pagehelper.Page;
import com.zzyl.dto.NursingProjectPlanDto;
import com.zzyl.entity.NursingPlan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 护理计划Mapper接口
 */
@Mapper
public interface NursingPlanMapper {

    Page<NursingPlan> selectByPage(@Param("name") String name, @Param("status") Integer status);

    /**
     * 查询所有护理计划
     * @return
     */
    List<NursingPlan> selectAll();

    void insert(NursingPlan nursingPlan);

    /**
     * 根据id查询护理计划
     * @param id
     * @return
     */
    NursingPlan selectById(Long id);

    void update(NursingPlan nursingPlan);

    void deleteById(Long id);

    /**
     * 根据计划ID查询关联的项目ID列表
     * @param planId
     * @return
     */
    List<Long> selectProjectIdsByPlanId(Long planId);

    /**
     * 根据计划ID查询护理项目计划详细信息（包含执行参数）
     * @param planId
     * @return
     */
    List<NursingProjectPlanDto> selectProjectPlanDetailsByPlanId(Long planId);

    /**
     * 插入护理计划项目关联关系
     * @param planId
     * @param projectId
     * @param createBy
     * @param executeTime
     * @param executeCycle
     * @param executeFrequency
     */
    void insertPlanProject(@Param("planId") Long planId, @Param("projectId") Long projectId, @Param("createBy") Long createBy,
                          @Param("executeTime") String executeTime, @Param("executeCycle") Integer executeCycle,
                          @Param("executeFrequency") Integer executeFrequency);

    /**
     * 删除护理计划项目关联关系
     * @param planId
     */
    void deletePlanProjectByPlanId(Long planId);
}