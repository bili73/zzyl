package com.zzyl.entity;

import com.zzyl.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * <p>
 * nursing_level 实体类
 * </p>
 *
 * @author system
 * @since 2024-01-02
 */
@Getter
@Setter
public class NursingLevel extends BaseEntity {

    /**
     * 护理等级名称
     */
    private String name;

    /**
     * 护理等级描述
     */
    private String description;

    /**
     * 护理费用（元/月）
     */
    private BigDecimal fee;

    /**
     * 状态（0：禁用，1：启用）
     */
    private Integer status;

    /**
     * 排序号
     */
    private Integer sortNo;

    /**
     * 关联的护理计划名称
     */
    private String planName;

    /**
     * 关联的护理计划ID
     */
    private Long planId;

}