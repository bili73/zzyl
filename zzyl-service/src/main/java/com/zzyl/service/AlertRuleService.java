package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.entity.AlertRule;
import com.zzyl.vo.AlertRuleVo;

import java.util.List;

/**
 * 告警规则Service接口
 */
public interface AlertRuleService {

    /**
     * 查询所有告警规则
     *
     * @return 告警规则列表
     */
    List<AlertRuleVo> getAll();

    /**
     * 分页查询告警规则
     *
     * @param iotId          物联网设备ID
     * @param productKey     产品密钥
     * @param alertRuleName  告警规则名称
     * @param functionName   功能名称
     * @param alertDataType  告警数据类型
     * @param status         状态
     * @param pageNum        页码
     * @param pageSize       每页大小
     * @return 分页结果
     */
    PageResponse<AlertRuleVo> getByPage(String iotId, String productKey, String alertRuleName,
                                       String functionName, Integer alertDataType, Integer status,
                                       Integer pageNum, Integer pageSize);

    /**
     * 根据ID查询告警规则
     *
     * @param id 告警规则ID
     * @return 告警规则信息
     */
    AlertRuleVo getById(Long id);

    /**
     * 根据物联网ID查询告警规则
     *
     * @param iotId 物联网设备ID
     * @return 告警规则列表
     */
    List<AlertRuleVo> getByIotId(String iotId);

    /**
     * 根据产品密钥查询告警规则
     *
     * @param productKey 产品密钥
     * @return 告警规则列表
     */
    List<AlertRuleVo> getByProductKey(String productKey);

    /**
     * 新增告警规则
     *
     * @param alertRule 告警规则信息
     */
    void add(AlertRule alertRule);

    /**
     * 修改告警规则
     *
     * @param alertRule 告警规则信息
     */
    void update(AlertRule alertRule);

    /**
     * 修改告警规则状态
     *
     * @param id     告警规则ID
     * @param status 状态
     */
    void updateStatus(Long id, Integer status);

    /**
     * 删除告警规则
     *
     * @param id 告警规则ID
     */
    void delete(Long id);

    /**
     * 批量删除告警规则
     *
     * @param ids 告警规则ID列表
     */
    void deleteByIds(List<Long> ids);
}
