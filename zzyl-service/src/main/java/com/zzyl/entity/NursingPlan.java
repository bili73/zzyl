package com.zzyl.entity;

import com.zzyl.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * <p>
 * nursing_plan 实体类
 * </p>
 *
 * @author system
 * @since 2024-01-02
 */
@Getter
@Setter
public class NursingPlan extends BaseEntity {

    /**
     * 计划名称
     */
    private String name;

    /**
     * 护理等级ID
     */
    private Long nursingLevelId;

    /**
     * 照护对象（老人ID）
     */
    private Long elderId;

    /**
     * 计划开始时间
     */
    private LocalDateTime startTime;

    /**
     * 计划结束时间
     */
    private LocalDateTime endTime;

    /**
     * 状态（0：禁用，1：启用）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

}