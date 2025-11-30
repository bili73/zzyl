package com.zzyl.service;

import com.github.pagehelper.Page;
import com.zzyl.entity.NursingProjectPlan;

public interface NursingProjectPlanService {

    /**
     * 分页查询护理项目计划关联列表
     * @param nursingProjectPlan 查询条件
     * @param pageNum 当前页码
     * @param pageSize 每页记录数
     * @return 分页结果
     */
    Page<NursingProjectPlan> selectNursingProjectPlanByPage(NursingProjectPlan nursingProjectPlan, Integer pageNum, Integer pageSize);

    /**
     * 根据ID获取护理项目计划关联
     * @param id 关联ID
     * @return 关联信息
     */
    NursingProjectPlan selectNursingProjectPlanById(Long id);

    /**
     * 新增护理项目计划关联
     * @param nursingProjectPlan 关联信息
     * @return 是否成功
     */
    Boolean insertNursingProjectPlan(NursingProjectPlan nursingProjectPlan);

    /**
     * 更新护理项目计划关联
     * @param nursingProjectPlan 关联信息
     * @return 是否成功
     */
    Boolean updateNursingProjectPlan(NursingProjectPlan nursingProjectPlan);

    /**
     * 删除护理项目计划关联
     * @param id 关联ID
     * @return 是否成功
     */
    Boolean deleteNursingProjectPlanById(Long id);

    /**
     * 根据计划ID获取关联的项目列表
     * @param planId 计划ID
     * @return 关联的项目列表
     */
    java.util.List<NursingProjectPlan> selectByPlanId(Long planId);

    /**
     * 根据项目ID获取关联的计划列表
     * @param projectId 项目ID
     * @return 关联的计划列表
     */
    java.util.List<NursingProjectPlan> selectByProjectId(Long projectId);

    /**
     * 批量删除护理项目计划关联
     * @param ids 关联ID列表
     * @return 是否成功
     */
    Boolean deleteNursingProjectPlanByIds(Long[] ids);
}