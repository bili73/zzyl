package com.zzyl.service;

import com.zzyl.base.PageResponse;
import com.zzyl.dto.ServiceOrderDto;
import com.zzyl.vo.ServiceOrderVo;

/**
 * 服务订单Service接口
 */
public interface ServiceOrderService {

    /**
     * 创建服务订单
     *
     * @param serviceOrderDto 订单数据
     * @return 订单ID
     */
    Long createOrder(ServiceOrderDto serviceOrderDto);

    /**
     * 根据ID获取订单详情
     *
     * @param id 订单ID
     * @return 订单详情
     */
    ServiceOrderVo getOrderById(Long id);

    /**
     * 分页查询订单列表（客户端）
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param status 订单状态（可选）
     * @return 分页结果
     */
    PageResponse<ServiceOrderVo> getOrdersByPage(Integer pageNum, Integer pageSize, Integer status);

    /**
     * 分页搜索订单列表（管理端）
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param orderNo 订单编号
     * @param elderName 老人姓名
     * @param projectName 护理项目名称
     * @param status 订单状态
     * @return 分页结果
     */
    PageResponse<ServiceOrderVo> searchOrders(Integer pageNum, Integer pageSize, String orderNo, String elderName, String projectName, Integer status);

    /**
     * 取消订单
     *
     * @param orderId 订单ID
     * @param reason 取消原因
     */
    void cancelOrder(Long orderId, String reason);

    /**
     * 订单退款
     *
     * @param serviceOrderDto 订单数据
     */
    void refundOrder(ServiceOrderDto serviceOrderDto);

    /**
     * 临时支付功能 - 更新订单状态为已支付
     *
     * @param orderId 订单ID
     */
    void updateOrderStatusToPaid(Long orderId);
}