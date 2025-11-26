package com.zzyl.entity;

import com.zzyl.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * nursing_plan_project 护理计划项目关联表 实体类
 * </p>
 *
 * @author system
 * @since 2024-01-02
 */
@Getter
@Setter
public class NursingPlanProject extends BaseEntity {

    /**
     * 护理计划ID
     */
    private Long planId;

    /**
     * 护理项目ID
     */
    private Long projectId;

}