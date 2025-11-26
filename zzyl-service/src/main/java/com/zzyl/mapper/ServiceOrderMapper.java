package com.zzyl.mapper;

import com.zzyl.entity.ServiceOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 服务订单Mapper接口
 */
@Mapper
public interface ServiceOrderMapper {

    /**
     * 插入订单
     *
     * @param serviceOrder 订单实体
     * @return 影响行数
     */
    int insert(ServiceOrder serviceOrder);

    /**
     * 根据ID查询订单
     *
     * @param id 订单ID
     * @return 订单实体
     */
    ServiceOrder selectById(Long id);

    /**
     * 根据用户ID和状态查询订单列表
     *
     * @param userId 用户ID
     * @param status 订单状态（可选）
     * @return 订单列表
     */
    List<ServiceOrder> selectByUserIdAndStatus(Long userId, Integer status);

    /**
     * 根据条件查询订单列表（管理端）
     *
     * @param orderNo 订单编号
     * @param elderName 老人姓名
     * @param projectName 护理项目名称
     * @param status 订单状态
     * @return 订单列表
     */
    List<ServiceOrder> selectByConditions(String orderNo, String elderName, String projectName, Integer status);

    /**
     * 更新订单
     *
     * @param serviceOrder 订单实体
     * @return 影响行数
     */
    int updateById(ServiceOrder serviceOrder);
}