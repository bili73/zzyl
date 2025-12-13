package com.zzyl.mapper;

import com.github.pagehelper.Page;
import com.zzyl.entity.NursingLevel;
import org.apache.ibatis.annotations.Mapper;

/**
 * 护理等级Mapper接口
 */
@Mapper
public interface NursingLevelMapper {

    Page<NursingLevel> selectByPage(String name, Integer status);

    /**
     * 查询所有护理等级
     * @return
     */
    java.util.List<NursingLevel> selectAll();

    void insert(NursingLevel nursingLevel);

    /**
     * 根据id查询护理等级
     * @param id
     * @return
     */
    NursingLevel selectById(Long id);

    void update(NursingLevel nursingLevel);

    void deleteById(Long id);

    /**
     * 根据护理计划ID查询护理等级
     * @param planId 护理计划ID
     * @return 护理等级列表
     */
    java.util.List<NursingLevel> selectByPlanId(Long planId);
}