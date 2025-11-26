package com.zzyl.controller.customer;

import com.zzyl.base.PageResponse;
import com.zzyl.base.ResponseResult;
import com.zzyl.controller.BaseController;
import com.zzyl.dto.ServiceOrderDto;
import com.zzyl.service.ServiceOrderService;
import com.zzyl.vo.ServiceOrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 小程序端-服务订单
 */
@RestController
@RequestMapping("/customer/orders")
@Api(tags = "小程序端-服务订单")
public class CustomerServiceOrderController extends BaseController {

    @Autowired
    private ServiceOrderService serviceOrderService;

    @PostMapping("/create")
    @ApiOperation("创建服务订单")
    public ResponseResult<Long> createOrder(@RequestBody ServiceOrderDto serviceOrderDto) {
        Long orderId = serviceOrderService.createOrder(serviceOrderDto);
        return success(orderId);
    }

    @GetMapping("/{id}")
    @ApiOperation("获取订单详情")
    public ResponseResult<ServiceOrderVo> getOrderById(@PathVariable Long id) {
        ServiceOrderVo orderVo = serviceOrderService.getOrderById(id);
        return success(orderVo);
    }

    @GetMapping("/page")
    @ApiOperation("分页查询订单列表")
    public ResponseResult<PageResponse<ServiceOrderVo>> getOrdersByPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer status) {
        PageResponse<ServiceOrderVo> pageResponse = serviceOrderService.getOrdersByPage(pageNum, pageSize, status);
        return success(pageResponse);
    }

    @PostMapping("/{orderId}/pay")
    @ApiOperation("临时支付功能")
    public ResponseResult<Void> tempPay(@PathVariable Long orderId) {
        serviceOrderService.updateOrderStatusToPaid(orderId);
        return success();
    }

    @PostMapping("/refund")
    @ApiOperation("申请退款")
    public ResponseResult<Void> refundOrder(@RequestBody Map<String, Object> refundData) {
        // 从请求数据中获取订单ID
        String productOrderNo = (String) refundData.get("productOrderNo");
        if (productOrderNo == null || productOrderNo.isEmpty()) {
            return error("订单ID不能为空");
        }

        try {
            Long orderId = Long.parseLong(productOrderNo);
            serviceOrderService.cancelOrder(orderId, (String) refundData.get("tradingChannel"));
            return success();
        } catch (NumberFormatException e) {
            return error("订单ID格式错误");
        }
    }

    @PostMapping("/{orderId}/cancel")
    @ApiOperation("取消订单")
    public ResponseResult<Void> cancelOrder(
            @PathVariable Long orderId,
            @RequestParam String reason) {
        serviceOrderService.cancelOrder(orderId, reason);
        return success();
    }
}