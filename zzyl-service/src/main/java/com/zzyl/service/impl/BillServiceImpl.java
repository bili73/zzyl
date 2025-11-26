package com.zzyl.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.entity.Bill;
import com.zzyl.entity.ServiceOrder;
import com.zzyl.mapper.BillMapper;
import com.zzyl.mapper.ServiceOrderMapper;
import com.zzyl.service.BillService;
import com.zzyl.utils.UserThreadLocal;
import com.zzyl.vo.BillVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 账单Service实现类
 */
@Slf4j
@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private ServiceOrderMapper serviceOrderMapper;

    @Override
    public PageResponse<BillVo> getBillsByPage(Integer pageNum, Integer pageSize, Integer transactionStatus, Long elderId) {
        log.info("分页查询账单列表: pageNum={}, pageSize={}, transactionStatus={}, elderId={}",
                 pageNum, pageSize, transactionStatus, elderId);

        // 获取当前用户ID
        Long userId = UserThreadLocal.getUserId();

        // 先尝试使用账单表查询
        try {
            // 开启分页
            PageHelper.startPage(pageNum, pageSize);

            // 查询账单列表
            List<Bill> bills = billMapper.selectByCondition(userId, transactionStatus, elderId);
            Page<Bill> page = (Page<Bill>) bills;

            // 如果没有账单数据，尝试从服务订单生成账单数据
            if (bills.isEmpty() && pageNum == 1) {
                generateBillsFromOrders(userId);
                // 重新查询
                PageHelper.startPage(pageNum, pageSize);
                bills = billMapper.selectByCondition(userId, transactionStatus, elderId);
                page = (Page<Bill>) bills;
            }

            // 转换为VO对象
            List<BillVo> billVos = bills.stream()
                    .map(this::convertToVo)
                    .collect(Collectors.toList());

            // 构建分页响应
            return PageResponse.<BillVo>builder()
                    .page(pageNum)
                    .pageSize(pageSize)
                    .pages((long) page.getPages())
                    .total(page.getTotal())
                    .records(billVos)
                    .build();
        } catch (Exception e) {
            // 如果账单表不存在，直接从订单表获取数据
            log.warn("账单表不存在，使用订单表数据: {}", e.getMessage());
            return getBillsFromOrders(userId, pageNum, pageSize, transactionStatus, elderId);
        }
    }

    /**
     * 从订单表获取账单数据（备用方案）
     */
    private PageResponse<BillVo> getBillsFromOrders(Long userId, Integer pageNum, Integer pageSize, Integer transactionStatus, Long elderId) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);

        // 查询用户的服务订单
        List<ServiceOrder> orders = serviceOrderMapper.selectByUserIdAndStatus(userId, null);
        Page<ServiceOrder> page = (Page<ServiceOrder>) orders;

        // 根据账单状态过滤订单
        List<ServiceOrder> filteredOrders = orders.stream()
                .filter(order -> {
                    if (transactionStatus == null) {
                        return true;
                    }
                    switch (transactionStatus) {
                        case 0: return order.getStatus() == 0; // 待支付
                        case 1: return order.getStatus() == 1 || order.getStatus() == 2 || order.getStatus() == 3; // 已支付
                        case 2: return order.getStatus() == 4; // 已退款
                        default: return true;
                    }
                })
                .filter(order -> elderId == null || elderId <= 0 || order.getElderId().equals(elderId))
                .collect(Collectors.toList());

        // 转换为账单VO对象
        List<BillVo> billVos = filteredOrders.stream()
                .map(this::convertOrderToBillVo)
                .collect(Collectors.toList());

        // 构建分页响应
        return PageResponse.<BillVo>builder()
                .page(pageNum)
                .pageSize(pageSize)
                .pages((long) page.getPages())
                .total((long) filteredOrders.size())
                .records(billVos)
                .build();
    }

    @Override
    public BillVo getBillById(Long id) {
        Bill bill = billMapper.selectById(id);
        if (bill == null) {
            return null;
        }

        return convertToVo(bill);
    }

    /**
     * 从服务订单生成账单数据
     */
    private void generateBillsFromOrders(Long userId) {
        log.info("为用户生成账单数据: userId={}", userId);

        // 查询用户的服务订单
        List<ServiceOrder> orders = serviceOrderMapper.selectByUserIdAndStatus(userId, null);

        for (ServiceOrder order : orders) {
            // 检查是否已经存在对应的账单
            Long existingCount = billMapper.countByOrderId(order.getId());

            if (existingCount > 0) {
                continue; // 已存在账单，跳过
            }

            // 创建账单
            Bill bill = new Bill();
            bill.setBillNo("BILL" + System.currentTimeMillis() + order.getId());
            bill.setOrderId(order.getId());
            bill.setOrderNo(order.getOrderNo());
            bill.setUserId(order.getUserId());
            bill.setElderId(order.getElderId());
            bill.setElderName(order.getElderName());
            bill.setProjectId(order.getProjectId());
            bill.setProjectName(order.getProjectName());
            bill.setAmount(order.getAmount());
            bill.setBillType(1); // 服务账单
            bill.setCreateTime(LocalDateTime.now());

            // 根据订单状态设置交易状态
            if (order.getStatus() == 0) {
                bill.setTransactionStatus(0); // 待支付
            } else if (order.getStatus() == 1) {
                bill.setTransactionStatus(1); // 已支付
                bill.setPayTime(order.getUpdateTime()); // 使用更新时间作为支付时间
            } else if (order.getStatus() == 4) {
                bill.setTransactionStatus(2); // 已退款
                bill.setRefundTime(order.getUpdateTime());
                bill.setRefundReason(order.getRemark());
            } else {
                bill.setTransactionStatus(1); // 其他状态视为已支付
            }

            billMapper.insert(bill);
        }

        log.info("账单数据生成完成");
    }

    /**
     * 转换为VO对象
     */
    private BillVo convertToVo(Bill bill) {
        BillVo billVo = new BillVo();
        BeanUtils.copyProperties(bill, billVo);

        // 初始化嵌套对象，避免前端null引用错误
        billVo.setNursingProjectVo(new BillVo.NursingProjectVo());
        billVo.setElderVo(new BillVo.ElderVo());
        billVo.setMemberVo(new BillVo.MemberVo());

        // 设置基本的嵌套对象信息
        billVo.getElderVo().setId(bill.getElderId());
        billVo.getElderVo().setName(bill.getElderName());

        billVo.getNursingProjectVo().setId(bill.getProjectId());
        billVo.getNursingProjectVo().setName(bill.getProjectName());

        return billVo;
    }

    /**
     * 将订单转换为账单VO对象
     */
    private BillVo convertOrderToBillVo(ServiceOrder order) {
        BillVo billVo = new BillVo();
        billVo.setId(order.getId());
        billVo.setBillNo("BILL" + order.getOrderNo());
        billVo.setOrderId(order.getId());
        billVo.setOrderNo(order.getOrderNo());
        billVo.setElderId(order.getElderId());
        billVo.setElderName(order.getElderName());
        billVo.setProjectName(order.getProjectName());
        billVo.setAmount(order.getAmount());
        billVo.setCreateTime(order.getCreateTime());
        billVo.setBillType(1); // 服务账单

        // 设置交易状态
        if (order.getStatus() == 0) {
            billVo.setTransactionStatus(0);
        } else if (order.getStatus() == 4) {
            billVo.setTransactionStatus(2);
            billVo.setRefundTime(order.getUpdateTime());
            billVo.setRefundReason(order.getRemark());
        } else {
            billVo.setTransactionStatus(1);
            billVo.setPayTime(order.getUpdateTime());
        }

        // 初始化嵌套对象
        billVo.setNursingProjectVo(new BillVo.NursingProjectVo());
        billVo.setElderVo(new BillVo.ElderVo());
        billVo.setMemberVo(new BillVo.MemberVo());

        billVo.getElderVo().setId(order.getElderId());
        billVo.getElderVo().setName(order.getElderName());
        billVo.getNursingProjectVo().setId(order.getProjectId());
        billVo.getNursingProjectVo().setName(order.getProjectName());

        return billVo;
    }
}