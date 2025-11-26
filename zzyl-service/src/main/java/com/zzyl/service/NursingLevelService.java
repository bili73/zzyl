package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.dto.NursingLevelDto;
import com.zzyl.vo.NursingLevelVo;

import java.util.List;

/**
 * 护理等级service接口
 */
public interface NursingLevelService {
    /**
     * 获取所有护理等级
     * @return
     */
    List<NursingLevelVo> getAll();

    /**
     * 分页查询护理等级
     * @param name
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageResponse<NursingLevelVo> getByPage(String name, Integer status, Integer pageNum, Integer pageSize);

    /**
     * 根据id查询护理等级
     * @param id
     * @return
     */
    NursingLevelVo getById(Long id);

    /**
     * 新增护理等级
     * @param nursingLevelDto
     */
    void add(NursingLevelDto nursingLevelDto);

    /**
     * 修改护理等级
     * @param nursingLevelDto
     */
    void update(NursingLevelDto nursingLevelDto);

    /**
     * 删除护理等级
     * @param id
     */
    void delete(Long id);

    /**
     * 修改护理等级状态
     * @param id
     * @param status
     */
    void updateStatus(Long id, Integer status);
}