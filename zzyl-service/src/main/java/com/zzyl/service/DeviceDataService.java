package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.entity.DeviceData;
import com.zzyl.vo.DeviceDataVo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 设备数据Service接口
 */
public interface DeviceDataService {

    /**
     * 查询所有设备数据
     *
     * @return 设备数据列表
     */
    List<DeviceDataVo> getAll();

    /**
     * 分页查询设备数据
     *
     * @param iotId      物联网设备ID
     * @param deviceName 设备名称
     * @param functionId 功能ID
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @param pageNum    页码
     * @param pageSize   每页大小
     * @return 分页结果
     */
    PageResponse<DeviceDataVo> getByPage(String iotId, String deviceName, String functionId,
                                        LocalDateTime startTime, LocalDateTime endTime,
                                        Integer pageNum, Integer pageSize);

    /**
     * 根据ID查询设备数据
     *
     * @param id 数据ID
     * @return 设备数据信息
     */
    DeviceDataVo getById(Long id);

    /**
     * 根据物联网ID查询设备数据
     *
     * @param iotId 物联网设备ID
     * @return 设备数据列表
     */
    List<DeviceDataVo> getByIotId(String iotId);

    /**
     * 根据时间范围查询设备数据
     *
     * @param iotId     物联网设备ID
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 设备数据列表
     */
    List<DeviceDataVo> getByTimeRange(String iotId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 新增设备数据
     *
     * @param deviceData 设备数据信息
     */
    void add(DeviceData deviceData);

    /**
     * 批量新增设备数据
     *
     * @param deviceDataList 设备数据列表
     */
    void addBatch(List<DeviceData> deviceDataList);

    /**
     * 修改设备数据
     *
     * @param deviceData 设备数据信息
     */
    void update(DeviceData deviceData);

    /**
     * 删除设备数据
     *
     * @param id 数据ID
     */
    void delete(Long id);

    /**
     * 批量删除设备数据
     *
     * @param ids 数据ID列表
     */
    void deleteByIds(List<Long> ids);
}
