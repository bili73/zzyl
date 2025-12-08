package com.zzyl.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.controller.BaseController;
import com.zzyl.dto.ReservationDto;
import com.zzyl.entity.Reservation;
import com.zzyl.mapper.ReservationMapper;
import com.zzyl.service.CustomerReservationService;
import com.zzyl.vo.ReservationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservation")
@Api(tags = "预约管理")
public class ReservationController extends BaseController {

    @Autowired
    private CustomerReservationService customerReservationService;

    @Autowired
    private ReservationMapper reservationMapper;

    @PostMapping("")
    @ApiOperation("新增预约")
    public ResponseResult<Long> addReservation(@RequestBody ReservationDto dto) {
        Long id = customerReservationService.addReservation(dto);
        return success(id);
    }

    @GetMapping("/page")
    @ApiOperation("分页查询预约列表")
    public ResponseResult<PageResponse<ReservationVo>> getList(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "startTime", required = false) Long startTime,
            @RequestParam(value = "endTime", required = false) Long endTime) {
        // 管理端查询所有预约，不限制用户，支持姓名、手机号、类型、状态、日期范围筛选
        PageHelper.startPage(pageNum, pageSize);
        Page<Reservation> page = reservationMapper.pageAll(name, phone, status, type,
            startTime != null ? new java.util.Date(startTime) : null,
            endTime != null ? new java.util.Date(endTime) : null);
        return success(PageResponse.of(page, ReservationVo.class));
    }

    @PutMapping("/{id}/cancel")
    @ApiOperation("取消预约")
    public ResponseResult<Void> cancelReservation(@PathVariable Long id) {
        customerReservationService.cancelReservation(id);
        return success();
    }

    @GetMapping("/{id}")
    @ApiOperation("查询预约详情")
    public ResponseResult<ReservationVo> getReservationDetail(@PathVariable Long id) {
        Reservation reservation = reservationMapper.selectById(id);
        if (reservation == null) {
            return error("预约不存在");
        }
        ReservationVo vo = new ReservationVo();
        BeanUtils.copyProperties(reservation, vo);
        return success(vo);
    }

    @GetMapping("/cancelled-count")
    @ApiOperation("查询取消预约数量")
    public ResponseResult<Integer> getCancelledReservationCount() {
        Integer count = customerReservationService.getCancelledReservationCount();
        return success(count);
    }

    @GetMapping("/countByTime")
    @ApiOperation("查询每个时间段剩余预约次数")
    public ResponseResult<List<ReservationVo>> countByTime() {
        List<ReservationVo> list = customerReservationService.countByTime();
        return success(list);
    }
}