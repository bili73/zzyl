package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.entity.AlertData;
import com.zzyl.vo.AlertDataVo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 告警数据Service接口
 */
public interface AlertDataService {

    /**
     * 查询所有告警数据
     *
     * @return 告警数据列表
     */
    List<AlertDataVo> getAll();

    /**
     * 分页查询告警数据
     *
     * @param iotId        物联网设备ID
     * @param deviceName   设备名称
     * @param alertRuleId  告警规则ID
     * @param type         告警类型
     * @param status       告警状态
     * @param startTime    开始时间
     * @param endTime      结束时间
     * @param pageNum      页码
     * @param pageSize     每页大小
     * @return 分页结果
     */
    PageResponse<AlertDataVo> getByPage(String iotId, String deviceName, Long alertRuleId,
                                       Integer type, Integer status,
                                       LocalDateTime startTime, LocalDateTime endTime,
                                       Integer pageNum, Integer pageSize);

    /**
     * 根据ID查询告警数据
     *
     * @param id 告警数据ID
     * @return 告警数据信息
     */
    AlertDataVo getById(Long id);

    /**
     * 根据物联网ID查询告警数据
     *
     * @param iotId 物联网设备ID
     * @return 告警数据列表
     */
    List<AlertDataVo> getByIotId(String iotId);

    /**
     * 根据告警规则ID查询告警数据
     *
     * @param alertRuleId 告警规则ID
     * @return 告警数据列表
     */
    List<AlertDataVo> getByAlertRuleId(Long alertRuleId);

    /**
     * 根据时间范围查询告警数据
     *
     * @param iotId      物联网设备ID
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 告警数据列表
     */
    List<AlertDataVo> getByTimeRange(String iotId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 新增告警数据
     *
     * @param alertData 告警数据信息
     */
    void add(AlertData alertData);

    /**
     * 批量新增告警数据
     *
     * @param alertDataList 告警数据列表
     */
    void addBatch(List<AlertData> alertDataList);

    /**
     * 修改告警数据
     *
     * @param alertData 告警数据信息
     */
    void update(AlertData alertData);

    /**
     * 处理告警
     *
     * @param id                告警数据ID
     * @param processingResult  处理结果
     * @param processorId       处理人ID
     * @param processorName     处理人姓名
     */
    void processAlert(Long id, String processingResult, Long processorId, String processorName);

    /**
     * 删除告警数据
     *
     * @param id 告警数据ID
     */
    void delete(Long id);

    /**
     * 批量删除告警数据
     *
     * @param ids 告警数据ID列表
     */
    void deleteByIds(List<Long> ids);
}
