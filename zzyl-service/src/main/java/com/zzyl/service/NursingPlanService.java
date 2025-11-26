package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.dto.NursingPlanDto;
import com.zzyl.vo.NursingPlanVo;

import java.util.List;

/**
 * 护理计划service接口
 */
public interface NursingPlanService {
    /**
     * 获取所有护理计划
     * @return
     */
    List<NursingPlanVo> getAll();

    /**
     * 分页查询护理计划
     * @param name
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResponse<NursingPlanVo> getByPage(String name, Integer status, Integer pageNum, Integer pageSize);

    /**
     * 根据id查询护理计划
     * @param id
     * @return
     */
    NursingPlanVo getById(Long id);

    /**
     * 新增护理计划
     * @param nursingPlanDto
     */
    void add(NursingPlanDto nursingPlanDto);

    /**
     * 修改护理计划
     * @param nursingPlanDto
     */
    void update(NursingPlanDto nursingPlanDto);

    /**
     * 删除护理计划
     * @param id
     */
    void delete(Long id);

    /**
     * 修改护理计划状态
     * @param id
     * @param status
     */
    void updateStatus(Long id, Integer status);
}