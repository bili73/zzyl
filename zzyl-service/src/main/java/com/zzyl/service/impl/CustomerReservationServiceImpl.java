package com.zzyl.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.ReservationDto;
import com.zzyl.entity.Visit;
import com.zzyl.mapper.VisitMapper;
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
    private VisitMapper visitMapper;

    @Override
    public Long addReservation(ReservationDto dto) {
        // 检查同一手机号同一时间段是否已预约
        List<Visit> existingReservations = visitMapper.findByMobileAndTime(dto.getMobile(), dto.getTime());
        if (!existingReservations.isEmpty()) {
            throw new RuntimeException("该手机号在当前时间段已有预约");
        }

        Visit visit = new Visit();
        BeanUtils.copyProperties(dto, visit);
        visit.setStatus(0); // 待报道状态
        visit.setCreateTime(LocalDateTime.now());
        visit.setCreateBy(UserThreadLocal.getUserId());

        visitMapper.insert(visit);
        return visit.getId();
    }

    @Override
    public void cancelReservation(Long id) {
        Visit visit = visitMapper.selectById(id);
        if (visit == null) {
            throw new RuntimeException("预约不存在");
        }

        // 检查是否为当前用户的预约
        Long currentUserId = UserThreadLocal.getUserId();
        if (!currentUserId.equals(visit.getCreateBy())) {
            throw new RuntimeException("无权限操作该预约");
        }

        if (visit.getStatus() != 0) {
            throw new RuntimeException("只能取消待报道的预约");
        }

        visit.setStatus(2); // 取消状态
        visit.setUpdateTime(LocalDateTime.now());
        visit.setUpdateBy(currentUserId);
        visitMapper.update(visit);
    }

    @Override
    public List<ReservationVo> listUserReservations(Integer status) {
        Long userId = UserThreadLocal.getUserId();
        List<Visit> visits;
        if (status != null) {
            visits = visitMapper.findByUserIdAndStatus(userId, status);
        } else {
            visits = visitMapper.findByUserId(userId);
        }
        return BeanUtil.copyToList(visits, ReservationVo.class);
    }

    @Override
    public PageResponse<ReservationVo> pageUserReservations(Integer pageNum, Integer pageSize, Integer status) {
        Long userId = UserThreadLocal.getUserId();
        PageHelper.startPage(pageNum, pageSize);
        Page<Visit> page;
        if (status != null) {
            page = visitMapper.pageByUserIdAndStatus(userId, status);
        } else {
            page = visitMapper.pageByUserId(userId);
        }
        return PageResponse.of(page, ReservationVo.class);
    }

    @Override
    public ReservationVo getReservationDetail(Long id) {
        Visit visit = visitMapper.selectById(id);
        if (visit == null) {
            throw new RuntimeException("预约不存在");
        }

        // 检查是否为当前用户的预约
        Long currentUserId = UserThreadLocal.getUserId();
        if (!currentUserId.equals(visit.getCreateBy())) {
            throw new RuntimeException("无权限查看该预约");
        }

        ReservationVo vo = new ReservationVo();
        BeanUtils.copyProperties(visit, vo);
        return vo;
    }

    @Override
    public Integer getCancelledReservationCount() {
        Long userId = UserThreadLocal.getUserId();
        return visitMapper.countByUserIdAndStatus(userId, 2); // 2表示取消状态
    }

    @Override
    public List<ReservationVo> countByTime() {
        Long userId = UserThreadLocal.getUserId();
        List<Visit> visits = visitMapper.findByUserId(userId);
        return BeanUtil.copyToList(visits, ReservationVo.class);
    }
}