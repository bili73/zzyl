package com.zzyl.mapper;

import com.zzyl.entity.AlertData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 告警数据Mapper接口
 */
@Mapper
public interface AlertDataMapper {

    /**
     * 根据ID查询告警数据
     *
     * @param id 告警数据ID
     * @return 告警数据信息
     */
    AlertData selectById(Long id);

    /**
     * 根据物联网ID查询告警数据
     *
     * @param iotId 物联网设备ID
     * @return 告警数据列表
     */
    List<AlertData> selectByIotId(String iotId);

    /**
     * 根据告警规则ID查询告警数据
     *
     * @param alertRuleId 告警规则ID
     * @return 告警数据列表
     */
    List<AlertData> selectByAlertRuleId(Long alertRuleId);

    /**
     * 查询所有告警数据
     *
     * @return 告警数据列表
     */
    List<AlertData> selectAll();

    /**
     * 根据条件查询告警数据
     *
     * @param alertData 查询条件
     * @return 告警数据列表
     */
    List<AlertData> selectByCondition(AlertData alertData);

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
     * @return 告警数据列表
     */
    List<AlertData> selectByPage(@Param("iotId") String iotId,
                                 @Param("deviceName") String deviceName,
                                 @Param("alertRuleId") Long alertRuleId,
                                 @Param("type") Integer type,
                                 @Param("status") Integer status,
                                 @Param("startTime") LocalDateTime startTime,
                                 @Param("endTime") LocalDateTime endTime);

    /**
     * 根据时间范围查询告警数据
     *
     * @param iotId      物联网设备ID
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 告警数据列表
     */
    List<AlertData> selectByTimeRange(@Param("iotId") String iotId,
                                      @Param("startTime") LocalDateTime startTime,
                                      @Param("endTime") LocalDateTime endTime);

    /**
     * 插入告警数据
     *
     * @param alertData 告警数据信息
     * @return 影响行数
     */
    int insert(AlertData alertData);

    /**
     * 批量插入告警数据
     *
     * @param alertDataList 告警数据列表
     * @return 影响行数
     */
    int insertBatch(List<AlertData> alertDataList);

    /**
     * 更新告警数据
     *
     * @param alertData 告警数据信息
     * @return 影响行数
     */
    int update(AlertData alertData);

    /**
     * 根据ID删除告警数据
     *
     * @param id 告警数据ID
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 批量删除告警数据
     *
     * @param ids 告警数据ID列表
     * @return 影响行数
     */
    int deleteByIds(@Param("ids") List<Long> ids);
}
