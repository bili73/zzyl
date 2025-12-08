package com.zzyl.mapper;

import com.github.pagehelper.Page;
import com.zzyl.entity.Reservation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 预约Mapper接口
 */
@Mapper
public interface ReservationMapper {

    /**
     * 插入预约
     */
    int insert(Reservation reservation);

    /**
     * 更新预约
     */
    int update(Reservation reservation);

    /**
     * 根据ID删除预约
     */
    int deleteById(Long id);

    /**
     * 根据ID查询预约
     */
    Reservation selectById(Long id);

    /**
     * 管理端分页查询所有预约
     */
    Page<Reservation> pageAll(
            @Param("name") String name,
            @Param("phone") String phone,
            @Param("status") Integer status,
            @Param("type") Integer type,
            @Param("startTime") java.util.Date startTime,
            @Param("endTime") java.util.Date endTime);

    /**
     * 根据手机号和时间查找预约
     */
    List<Reservation> findByMobileAndTime(@Param("mobile") String mobile, @Param("time") LocalDateTime time);

    /**
     * 根据用户ID和状态查找预约
     */
    List<Reservation> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") Integer status);

    /**
     * 根据用户ID查找预约
     */
    List<Reservation> findByUserId(Long userId);

    /**
     * 分页根据用户ID和状态查找预约
     */
    Page<Reservation> pageByUserIdAndStatus(@Param("userId") Long userId, @Param("status") Integer status);

    /**
     * 分页根据用户ID查找预约
     */
    Page<Reservation> pageByUserId(Long userId);

    /**
     * 根据用户ID和状态统计预约数量
     */
    Integer countByUserIdAndStatus(@Param("userId") Long userId, @Param("status") Integer status);

    /**
     * 根据用户ID统计预约数量
     */
    Integer countByUserId(Long userId);

    /**
     * 过期超时的预约
     */
    int expireOverdueReservations(@Param("currentTime") LocalDateTime currentTime);
}