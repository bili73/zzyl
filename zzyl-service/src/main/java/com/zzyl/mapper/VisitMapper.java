package com.zzyl.mapper;

import com.github.pagehelper.Page;
import com.zzyl.entity.Visit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface VisitMapper {

    int insert(Visit visit);

    int update(Visit visit);

    int deleteById(Long id);

    Visit findById(Long id);

    Visit selectById(Long id);

    List<Visit> findAll(@Param("mobile") String mobile, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    Page<Visit> findByPage(@Param("page") int startIndex, @Param("pageSize") int pageSize, @Param("name") String name, @Param("mobile") String mobile, @Param("status") Integer status, @Param("type") Integer type, @Param("createBy") Long userId, @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    /**
     * 根据手机号和时间查找预约
     * @param mobile 手机号
     * @param time 时间
     * @return 预约列表
     */
    List<Visit> findByMobileAndTime(@Param("mobile") String mobile, @Param("time") LocalDateTime time);

    /**
     * 根据用户ID和状态查找预约
     * @param userId 用户ID
     * @param status 状态
     * @return 预约列表
     */
    List<Visit> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status") Integer status);

    /**
     * 根据用户ID查找预约
     * @param userId 用户ID
     * @return 预约列表
     */
    List<Visit> findByUserId(@Param("userId") Long userId);

    /**
     * 分页根据用户ID和状态查找预约
     * @param userId 用户ID
     * @param status 状态
     * @return 分页结果
     */
    Page<Visit> pageByUserIdAndStatus(@Param("userId") Long userId, @Param("status") Integer status);

    /**
     * 分页根据用户ID查找预约
     * @param userId 用户ID
     * @return 分页结果
     */
    Page<Visit> pageByUserId(@Param("userId") Long userId);

    /**
     * 根据用户ID和状态统计预约数量
     * @param userId 用户ID
     * @param status 状态
     * @return 数量
     */
    Integer countByUserIdAndStatus(@Param("userId") Long userId, @Param("status") Integer status);

    /**
     * 根据用户ID统计预约数量
     * @param userId 用户ID
     * @return 数量
     */
    Integer countByUserId(@Param("userId") Long userId);

    /**
     * 过期超时的预约
     * @param currentTime 当前时间
     * @return 更新的记录数
     */
    Integer expireOverdueReservations(@Param("currentTime") LocalDateTime currentTime);
}
