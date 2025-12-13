package com.zzyl.mapper;

import com.zzyl.entity.DeviceData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 设备数据Mapper接口
 */
@Mapper
public interface DeviceDataMapper {

    /**
     * 根据ID查询设备数据
     *
     * @param id 数据ID
     * @return 设备数据信息
     */
    DeviceData selectById(Long id);

    /**
     * 根据物联网ID查询设备数据
     *
     * @param iotId 物联网设备ID
     * @return 设备数据列表
     */
    List<DeviceData> selectByIotId(String iotId);

    /**
     * 查询所有设备数据
     *
     * @return 设备数据列表
     */
    List<DeviceData> selectAll();

    /**
     * 根据条件查询设备数据
     *
     * @param deviceData 查询条件
     * @return 设备数据列表
     */
    List<DeviceData> selectByCondition(DeviceData deviceData);

    /**
     * 分页查询设备数据
     *
     * @param iotId      物联网设备ID
     * @param deviceName 设备名称
     * @param functionId 功能ID
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 设备数据列表
     */
    List<DeviceData> selectByPage(@Param("iotId") String iotId,
                                  @Param("deviceName") String deviceName,
                                  @Param("functionId") String functionId,
                                  @Param("startTime") LocalDateTime startTime,
                                  @Param("endTime") LocalDateTime endTime);

    /**
     * 根据时间范围查询设备数据
     *
     * @param iotId    物联网设备ID
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 设备数据列表
     */
    List<DeviceData> selectByTimeRange(@Param("iotId") String iotId,
                                       @Param("startTime") LocalDateTime startTime,
                                       @Param("endTime") LocalDateTime endTime);

    /**
     * 插入设备数据
     *
     * @param deviceData 设备数据信息
     * @return 影响行数
     */
    int insert(DeviceData deviceData);

    /**
     * 批量插入设备数据
     *
     * @param deviceDataList 设备数据列表
     * @return 影响行数
     */
    int insertBatch(List<DeviceData> deviceDataList);

    /**
     * 更新设备数据
     *
     * @param deviceData 设备数据信息
     * @return 影响行数
     */
    int update(DeviceData deviceData);

    /**
     * 根据ID删除设备数据
     *
     * @param id 数据ID
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 批量删除设备数据
     *
     * @param ids 数据ID列表
     * @return 影响行数
     */
    int deleteByIds(@Param("ids") List<Long> ids);
}
