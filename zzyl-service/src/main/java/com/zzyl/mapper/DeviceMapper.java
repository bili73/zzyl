package com.zzyl.mapper;

import com.zzyl.entity.Device;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设备Mapper接口
 */
@Mapper
public interface DeviceMapper {

    /**
     * 根据ID查询设备
     *
     * @param id 设备ID
     * @return 设备信息
     */
    Device selectById(Long id);

    /**
     * 根据物联网ID查询设备
     *
     * @param iotId 物联网设备ID
     * @return 设备信息
     */
    Device selectByIotId(String iotId);

    /**
     * 查询所有设备
     *
     * @return 设备列表
     */
    List<Device> selectAll();

    /**
     * 根据条件查询设备
     *
     * @param device 查询条件
     * @return 设备列表
     */
    List<Device> selectByCondition(Device device);

    /**
     * 分页查询设备
     *
     * @param deviceName 设备名称
     * @param iotId      物联网设备ID
     * @return 设备列表
     */
    List<Device> selectByPage(@Param("deviceName") String deviceName, @Param("iotId") String iotId);

    /**
     * 插入设备
     *
     * @param device 设备信息
     * @return 影响行数
     */
    int insert(Device device);

    /**
     * 更新设备
     *
     * @param device 设备信息
     * @return 影响行数
     */
    int update(Device device);

    /**
     * 根据ID删除设备
     *
     * @param id 设备ID
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 批量删除设备
     *
     * @param ids 设备ID列表
     * @return 影响行数
     */
    int deleteByIds(@Param("ids") List<Long> ids);
}
