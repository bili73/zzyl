package com.zzyl.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.ReservationDto;
import com.zzyl.entity.Reservation;
import com.zzyl.mapper.ReservationMapper;
import com.zzyl.service.CustomerReservationService;
import com.zzyl.utils.UserThreadLocal;
import com.zzyl.vo.ReservationVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import cn.hutool.core.bean.BeanUtil;

/**
 * 小程序端预约服务实现类
 */
@Service
public class CustomerReservationServiceImpl implements CustomerReservationService {

    @Autowired
    private ReservationMapper reservationMapper;

    @Override
    public Long addReservation(ReservationDto dto) {
        // 检查同一手机号同一时间段是否已预约
        List<Reservation> existingReservations = reservationMapper.findByMobileAndTime(dto.getMobile(), dto.getTime());
        if (!existingReservations.isEmpty()) {
            throw new RuntimeException("该手机号在当前时间段已有预约");
        }

        Reservation reservation = new Reservation();
        BeanUtils.copyProperties(dto, reservation);
        reservation.setStatus(0); // 待报道状态
        reservation.setCreateTime(LocalDateTime.now());
        reservation.setCreateBy(UserThreadLocal.getUserId());

        reservationMapper.insert(reservation);
        return reservation.getId();
    }

    @Override
    public void cancelReservation(Long id) {
        Reservation reservation = reservationMapper.selectById(id);
        if (reservation == null) {
            throw new RuntimeException("预约不存在");
        }

        // 检查是否为当前用户的预约
        Long currentUserId = UserThreadLocal.getUserId();
        if (!currentUserId.equals(reservation.getCreateBy())) {
            throw new RuntimeException("无权限操作该预约");
        }

        if (reservation.getStatus() != 0) {
            throw new RuntimeException("只能取消待报道的预约");
        }

        reservation.setStatus(2); // 取消状态
        reservation.setUpdateTime(LocalDateTime.now());
        reservation.setUpdateBy(currentUserId);
        reservationMapper.update(reservation);
    }

    @Override
    public List<ReservationVo> listUserReservations(Integer status) {
        Long userId = UserThreadLocal.getUserId();
        List<Reservation> reservations;
        if (status != null) {
            reservations = reservationMapper.findByUserIdAndStatus(userId, status);
        } else {
            reservations = reservationMapper.findByUserId(userId);
        }
        return BeanUtil.copyToList(reservations, ReservationVo.class);
    }

    @Override
    public PageResponse<ReservationVo> pageUserReservations(Integer pageNum, Integer pageSize, Integer status) {
        Long userId = UserThreadLocal.getUserId();
        PageHelper.startPage(pageNum, pageSize);
        Page<Reservation> page;
        if (status != null) {
            page = reservationMapper.pageByUserIdAndStatus(userId, status);
        } else {
            page = reservationMapper.pageByUserId(userId);
        }
        return PageResponse.of(page, ReservationVo.class);
    }

    @Override
    public ReservationVo getReservationDetail(Long id) {
        Reservation reservation = reservationMapper.selectById(id);
        if (reservation == null) {
            throw new RuntimeException("预约不存在");
        }

        // 检查是否为当前用户的预约
        Long currentUserId = UserThreadLocal.getUserId();
        if (!currentUserId.equals(reservation.getCreateBy())) {
            throw new RuntimeException("无权限查看该预约");
        }

        ReservationVo vo = new ReservationVo();
        BeanUtils.copyProperties(reservation, vo);
        return vo;
    }

    @Override
    public Integer getCancelledReservationCount() {
        Long userId = UserThreadLocal.getUserId();
        return reservationMapper.countByUserIdAndStatus(userId, 2); // 2表示取消状态
    }

    @Override
    public List<ReservationVo> countByTime() {
        Long userId = UserThreadLocal.getUserId();
        List<Reservation> reservations = reservationMapper.findByUserId(userId);
        return BeanUtil.copyToList(reservations, ReservationVo.class);
    }
}