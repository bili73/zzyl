package com.zzyl.controller;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.dto.ServiceOrderDto;
import com.zzyl.service.ServiceOrderService;
import com.zzyl.vo.ServiceOrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 管理端-服务订单
 */
@RestController
@RequestMapping("/orders")
@Api(tags = "管理端-服务订单")
public class ServiceOrderController extends BaseController {

    @Autowired
    private ServiceOrderService serviceOrderService;

    @GetMapping("/search")
    @ApiOperation("分页查询订单列表")
    public ResponseResult<PageResponse<ServiceOrderVo>> searchOrders(
            @ApiParam(value = "页码", defaultValue = "1") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页大小", defaultValue = "10") @RequestParam(defaultValue = "10") Integer pageSize,
            @ApiParam(value = "订单编号") @RequestParam(required = false) String orderNo,
            @ApiParam(value = "老人姓名") @RequestParam(required = false) String elderName,
            @ApiParam(value = "护理项目名称") @RequestParam(required = false) String projectName,
            @ApiParam(value = "订单状态") @RequestParam(required = false) Integer status) {

        PageResponse<ServiceOrderVo> pageResponse = serviceOrderService.searchOrders(pageNum, pageSize, orderNo, elderName, projectName, status);
        return success(pageResponse);
    }

    @GetMapping
    @ApiOperation("获取订单详情")
    public ResponseResult<ServiceOrderVo> getOrderById(@RequestParam Long orderId) {
        ServiceOrderVo orderVo = serviceOrderService.getOrderById(orderId);
        return success(orderVo);
    }

    @PostMapping("/{orderId}/cancel")
    @ApiOperation("取消订单")
    public ResponseResult<Void> cancelOrder(
            @PathVariable Long orderId,
            @RequestParam String reason) {
        serviceOrderService.cancelOrder(orderId, reason);
        return success();
    }

    @PostMapping("/refund")
    @ApiOperation("订单退款")
    public ResponseResult<Void> refundOrder(@RequestBody ServiceOrderDto serviceOrderDto) {
        serviceOrderService.refundOrder(serviceOrderDto);
        return success();
    }
}