package com.zzyl.mapper;

import com.zzyl.entity.AlertRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 告警规则Mapper接口
 */
@Mapper
public interface AlertRuleMapper {

    /**
     * 根据ID查询告警规则
     *
     * @param id 告警规则ID
     * @return 告警规则信息
     */
    AlertRule selectById(Long id);

    /**
     * 根据物联网ID查询告警规则
     *
     * @param iotId 物联网设备ID
     * @return 告警规则列表
     */
    List<AlertRule> selectByIotId(String iotId);

    /**
     * 根据产品密钥查询告警规则
     *
     * @param productKey 产品密钥
     * @return 告警规则列表
     */
    List<AlertRule> selectByProductKey(String productKey);

    /**
     * 查询所有告警规则
     *
     * @return 告警规则列表
     */
    List<AlertRule> selectAll();

    /**
     * 根据条件查询告警规则
     *
     * @param alertRule 查询条件
     * @return 告警规则列表
     */
    List<AlertRule> selectByCondition(AlertRule alertRule);

    /**
     * 分页查询告警规则
     *
     * @param iotId          物联网设备ID
     * @param productKey     产品密钥
     * @param alertRuleName  告警规则名称
     * @param functionName   功能名称
     * @param alertDataType  告警数据类型
     * @param status         状态
     * @return 告警规则列表
     */
    List<AlertRule> selectByPage(@Param("iotId") String iotId,
                                 @Param("productKey") String productKey,
                                 @Param("alertRuleName") String alertRuleName,
                                 @Param("functionName") String functionName,
                                 @Param("alertDataType") Integer alertDataType,
                                 @Param("status") Integer status);

    /**
     * 插入告警规则
     *
     * @param alertRule 告警规则信息
     * @return 影响行数
     */
    int insert(AlertRule alertRule);

    /**
     * 更新告警规则
     *
     * @param alertRule 告警规则信息
     * @return 影响行数
     */
    int update(AlertRule alertRule);

    /**
     * 根据ID删除告警规则
     *
     * @param id 告警规则ID
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 批量删除告警规则
     *
     * @param ids 告警规则ID列表
     * @return 影响行数
     */
    int deleteByIds(@Param("ids") List<Long> ids);
}
