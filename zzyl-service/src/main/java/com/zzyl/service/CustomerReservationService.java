package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.dto.ReservationDto;
import com.zzyl.vo.ReservationVo;

import java.util.List;

/**
 * 小程序端预约服务接口
 */
public interface CustomerReservationService {

    /**
     * 新增预约
     * @param dto 预约信息
     * @return 预约ID
     */
    Long addReservation(ReservationDto dto);

    /**
     * 取消预约
     * @param id 预约ID
     */
    void cancelReservation(Long id);

    /**
     * 查询用户的预约列表
     * @param status 预约状态，可为空
     * @return 预约列表
     */
    List<ReservationVo> listUserReservations(Integer status);

    /**
     * 分页查询用户的预约列表
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param status 预约状态，可为空
     * @return 分页结果
     */
    PageResponse<ReservationVo> pageUserReservations(Integer pageNum, Integer pageSize, Integer status);

    /**
     * 查询预约详情
     * @param id 预约ID
     * @return 预约详情
     */
    ReservationVo getReservationDetail(Long id);

    /**
     * 查询取消预约数量
     * @return 取消预约数量
     */
    Integer getCancelledReservationCount();

    /**
     * 查询每个时间段剩余预约次数
     * @return 预约列表
     */
    List<ReservationVo> countByTime();
}