package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.entity.Device;
import com.zzyl.vo.DeviceVo;

import java.util.List;

/**
 * 设备Service接口
 */
public interface DeviceService {

    /**
     * 查询所有设备
     *
     * @return 设备列表
     */
    List<DeviceVo> getAll();

    /**
     * 分页查询设备
     *
     * @param deviceName 设备名称（模糊查询）
     * @param iotId      物联网设备ID
     * @param pageNum    页码
     * @param pageSize   每页大小
     * @return 分页结果
     */
    PageResponse<DeviceVo> getByPage(String deviceName, String iotId, Integer pageNum, Integer pageSize);

    /**
     * 根据ID查询设备
     *
     * @param id 设备ID
     * @return 设备信息
     */
    DeviceVo getById(Long id);

    /**
     * 根据物联网ID查询设备
     *
     * @param iotId 物联网设备ID
     * @return 设备信息
     */
    Device getByIotId(String iotId);

    /**
     * 新增设备
     *
     * @param device 设备信息
     */
    void add(Device device);

    /**
     * 修改设备
     *
     * @param device 设备信息
     */
    void update(Device device);

    /**
     * 删除设备
     *
     * @param id 设备ID
     */
    void delete(Long id);

    /**
     * 批量删除设备
     *
     * @param ids 设备ID列表
     */
    void deleteByIds(List<Long> ids);
}
