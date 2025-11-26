package com.zzyl.controller.customer;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.controller.BaseController;
import com.zzyl.dto.ReservationDto;
import com.zzyl.service.CustomerReservationService;
import com.zzyl.utils.UserThreadLocal;
import com.zzyl.vo.ReservationVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer/reservation")
@Api(tags = "客户预约管理")
public class CustomerReservationController extends BaseController {

    @Autowired
    private CustomerReservationService customerReservationService;

    @PostMapping("")
    @ApiOperation("新增预约")
    public ResponseResult<Long> addReservation(@RequestBody ReservationDto dto) {
        Long id = customerReservationService.addReservation(dto);
        return success(id);
    }

    @GetMapping("/page")
    @ApiOperation("分页查询预约列表")
    public ResponseResult<PageResponse<ReservationVo>> getList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer status) {
        PageResponse<ReservationVo> pageResponse = customerReservationService.pageUserReservations(pageNum, pageSize, status);
        return success(pageResponse);
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
        ReservationVo vo = customerReservationService.getReservationDetail(id);
        return success(vo);
    }

    @GetMapping("/cancelled-count")
    @ApiOperation("查询取消预约数量")
    public ResponseResult<Integer> getCancelledReservationCount() {
        Long userId = UserThreadLocal.getUserId();
        System.out.println("getCancelledReservationCount called, userId: " + userId);
        Integer count = customerReservationService.getCancelledReservationCount();
        return success(count);
    }

    @GetMapping("/countByTime")
    @ApiOperation("查询每个时间段剩余预约次数")
    public ResponseResult<List<ReservationVo>> getAllList() {
        Long userId = UserThreadLocal.getUserId();
        System.out.println("countByTime called, userId: " + userId);
        List<ReservationVo> list = customerReservationService.countByTime();
        return success(list);
    }
}