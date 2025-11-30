package com.zzyl.service;

import com.github.pagehelper.Page;
import com.zzyl.entity.Reservation;

public interface ReservationService {

    /**
     * 分页查询预约列表
     * @param reservation 预约查询条件
     * @param pageNum 当前页码
     * @param pageSize 每页记录数
     * @return 分页结果
     */
    Page<Reservation> selectReservationByPage(Reservation reservation, Integer pageNum, Integer pageSize);

    /**
     * 根据ID获取预约
     * @param id 预约ID
     * @return 预约信息
     */
    Reservation selectReservationById(Long id);

    /**
     * 新增预约
     * @param reservation 预约信息
     * @return 是否成功
     */
    Boolean insertReservation(Reservation reservation);

    /**
     * 更新预约
     * @param reservation 预约信息
     * @return 是否成功
     */
    Boolean updateReservation(Reservation reservation);

    /**
     * 删除预约
     * @param id 预约ID
     * @return 是否成功
     */
    Boolean deleteReservationById(Long id);

    /**
     * 取消预约
     * @param id 预约ID
     * @return 是否成功
     */
    Boolean cancelReservation(Long id);

    /**
     * 完成预约
     * @param id 预约ID
     * @return 是否成功
     */
    Boolean completeReservation(Long id);

    /**
     * 获取某个时间段的所有可用时间
     * @param date 日期
     * @return 可用时间列表
     */
    java.util.List<String> getAvailableTime(String date);
}