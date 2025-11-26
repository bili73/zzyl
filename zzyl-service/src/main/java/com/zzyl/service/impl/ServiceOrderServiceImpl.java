package com.zzyl.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zzyl.base.PageResponse;
import com.zzyl.dto.ServiceOrderDto;
import com.zzyl.entity.NursingProject;
import com.zzyl.entity.ServiceOrder;
import com.zzyl.entity.Member;
import com.zzyl.entity.Elder;
import com.zzyl.entity.Bed;
import com.zzyl.mapper.NursingProjectMapper;
import com.zzyl.mapper.ServiceOrderMapper;
import com.zzyl.mapper.MemberMapper;
import com.zzyl.mapper.ElderMapper;
import com.zzyl.mapper.BedMapper;
import com.zzyl.service.ServiceOrderService;
import com.zzyl.utils.UserThreadLocal;
import com.zzyl.vo.ServiceOrderVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务订单Service实现类
 */
@Slf4j
@Service
public class ServiceOrderServiceImpl implements ServiceOrderService {

    @Autowired
    private ServiceOrderMapper serviceOrderMapper;

    @Autowired
    private NursingProjectMapper nursingProjectMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private ElderMapper elderMapper;

    @Autowired
    private BedMapper bedMapper;

    @Override
    public Long createOrder(ServiceOrderDto serviceOrderDto) {
        log.info("创建服务订单: {}", serviceOrderDto);

        // 获取护理项目信息
        NursingProject nursingProject = nursingProjectMapper.selectById(serviceOrderDto.getProjectId());
        if (nursingProject == null) {
            throw new RuntimeException("护理项目不存在");
        }

        ServiceOrder serviceOrder = new ServiceOrder();
        BeanUtils.copyProperties(serviceOrderDto, serviceOrder);

        // 手动设置老人姓名（因为DTO中的字段名是name，实体中的字段名是elderName）
        serviceOrder.setElderName(serviceOrderDto.getName());

        // 生成订单编号
        serviceOrder.setOrderNo(generateOrderNo());
        serviceOrder.setProjectName(nursingProject.getName());
        serviceOrder.setAmount(nursingProject.getPrice());
        serviceOrder.setStatus(0); // 默认状态：待支付

        // 处理期望服务时间
        log.info("收到的期望服务时间: {}", serviceOrderDto.getEstimatedArrivalTime());
        if (serviceOrderDto.getEstimatedArrivalTime() != null && !serviceOrderDto.getEstimatedArrivalTime().trim().isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                serviceOrder.setEstimatedArrivalTime(LocalDateTime.parse(serviceOrderDto.getEstimatedArrivalTime(), formatter));
                log.info("解析后的期望服务时间: {}", serviceOrder.getEstimatedArrivalTime());
            } catch (Exception e) {
                log.error("期望服务时间格式解析失败: {}", serviceOrderDto.getEstimatedArrivalTime(), e);
                // 可以设置一个默认时间或者设置为null，根据业务需求
                serviceOrder.setEstimatedArrivalTime(null);
            }
        }

        serviceOrder.setCreateTime(LocalDateTime.now());

        // 从用户上下文中获取用户ID
        Long userId = UserThreadLocal.getUserId();
        serviceOrder.setUserId(userId);
        serviceOrder.setCreateBy(userId);

        serviceOrderMapper.insert(serviceOrder);

        log.info("服务订单创建成功，订单ID: {}", serviceOrder.getId());
        return serviceOrder.getId();
    }

    @Override
    public ServiceOrderVo getOrderById(Long id) {
        ServiceOrder serviceOrder = serviceOrderMapper.selectById(id);
        if (serviceOrder == null) {
            return null;
        }

        ServiceOrderVo serviceOrderVo = new ServiceOrderVo();
        BeanUtils.copyProperties(serviceOrder, serviceOrderVo);
        serviceOrderVo.setStatusDesc(getStatusDesc(serviceOrder.getStatus()));
        serviceOrderVo.setPaymentStatus(mapToPaymentStatus(serviceOrder.getStatus()));

        // 初始化退款记录和护理任务对象，避免前端null引用错误
        serviceOrderVo.setRefundRecordVo(new ServiceOrderVo.RefundRecordVo());
        serviceOrderVo.setNursingTaskVo(new ServiceOrderVo.NursingTaskVo());

        // 查询护理项目信息
        NursingProject nursingProject = nursingProjectMapper.selectById(serviceOrder.getProjectId());
        if (nursingProject != null) {
            // 设置护理项目相关字段
            serviceOrderVo.setProjectImage(nursingProject.getImage());
            serviceOrderVo.setProjectUnit(nursingProject.getUnit());

            // 设置嵌套对象（兼容前端）
            ServiceOrderVo.NursingProjectVo nursingProjectVo = new ServiceOrderVo.NursingProjectVo();
            nursingProjectVo.setId(nursingProject.getId());
            nursingProjectVo.setName(nursingProject.getName());
            nursingProjectVo.setImage(nursingProject.getImage());
            nursingProjectVo.setUnit(nursingProject.getUnit());
            nursingProjectVo.setPrice(nursingProject.getPrice());
            serviceOrderVo.setNursingProjectVo(nursingProjectVo);
        }

        // 设置老人信息嵌套对象（兼容前端）
        ServiceOrderVo.ElderVo elderVo = new ServiceOrderVo.ElderVo();
        elderVo.setId(serviceOrder.getElderId());
        elderVo.setName(serviceOrder.getElderName());
        serviceOrderVo.setElderVo(elderVo);

        // 查询下单人信息
        Member member = memberMapper.selectById(serviceOrder.getUserId());
        if (member != null) {
            ServiceOrderVo.MemberVo memberVo = new ServiceOrderVo.MemberVo();
            memberVo.setId(member.getId());
            memberVo.setName(member.getName());
            serviceOrderVo.setMemberVo(memberVo);
        }

        // 查询老人详细信息获取床位信息
        Elder elder = elderMapper.selectById(serviceOrder.getElderId());
        if (elder != null && elder.getBedId() != null) {
            Bed bed = bedMapper.selectById(elder.getBedId());
            if (bed != null) {
                ServiceOrderVo.BedVo bedVo = new ServiceOrderVo.BedVo();
                bedVo.setId(bed.getId());
                bedVo.setBedNumber(bed.getBedNumber());
                serviceOrderVo.setBedVo(bedVo);
            }
        }

        return serviceOrderVo;
    }

    @Override
    public PageResponse<ServiceOrderVo> getOrdersByPage(Integer pageNum, Integer pageSize, Integer status) {
        // 获取当前用户ID
        Long userId = UserThreadLocal.getUserId();

        // 开启分页
        PageHelper.startPage(pageNum, pageSize);

        // 查询订单列表
        List<ServiceOrder> serviceOrders = serviceOrderMapper.selectByUserIdAndStatus(userId, status);
        Page<ServiceOrder> page = (Page<ServiceOrder>) serviceOrders;

        // 转换为VO对象
        List<ServiceOrderVo> serviceOrderVos = serviceOrders.stream()
                .map(serviceOrder -> {
                    ServiceOrderVo serviceOrderVo = new ServiceOrderVo();
                    BeanUtils.copyProperties(serviceOrder, serviceOrderVo);
                    serviceOrderVo.setStatusDesc(getStatusDesc(serviceOrder.getStatus()));

                    // 映射交易状态（兼容前端）
                    serviceOrderVo.setPaymentStatus(mapToPaymentStatus(serviceOrder.getStatus()));

                    // 初始化退款记录和护理任务对象，避免前端null引用错误
                    serviceOrderVo.setRefundRecordVo(new ServiceOrderVo.RefundRecordVo());
                    serviceOrderVo.setNursingTaskVo(new ServiceOrderVo.NursingTaskVo());

                    // 查询护理项目信息
                    NursingProject nursingProject = nursingProjectMapper.selectById(serviceOrder.getProjectId());
                    if (nursingProject != null) {
                        // 设置护理项目相关字段
                        serviceOrderVo.setProjectImage(nursingProject.getImage());
                        serviceOrderVo.setProjectUnit(nursingProject.getUnit());

                        // 设置嵌套对象（兼容前端）
                        ServiceOrderVo.NursingProjectVo nursingProjectVo = new ServiceOrderVo.NursingProjectVo();
                        nursingProjectVo.setId(nursingProject.getId());
                        nursingProjectVo.setName(nursingProject.getName());
                        nursingProjectVo.setImage(nursingProject.getImage());
                        nursingProjectVo.setUnit(nursingProject.getUnit());
                        nursingProjectVo.setPrice(nursingProject.getPrice());
                        serviceOrderVo.setNursingProjectVo(nursingProjectVo);
                    }

                    // 设置老人信息嵌套对象（兼容前端）
                    ServiceOrderVo.ElderVo elderVo = new ServiceOrderVo.ElderVo();
                    elderVo.setId(serviceOrder.getElderId());
                    elderVo.setName(serviceOrder.getElderName());
                    serviceOrderVo.setElderVo(elderVo);

                    // 查询下单人信息
                    Member member = memberMapper.selectById(serviceOrder.getUserId());
                    if (member != null) {
                        ServiceOrderVo.MemberVo memberVo = new ServiceOrderVo.MemberVo();
                        memberVo.setId(member.getId());
                        memberVo.setName(member.getName());
                        serviceOrderVo.setMemberVo(memberVo);
                    }

                    // 查询老人详细信息获取床位信息
                    Elder elder = elderMapper.selectById(serviceOrder.getElderId());
                    if (elder != null && elder.getBedId() != null) {
                        Bed bed = bedMapper.selectById(elder.getBedId());
                        if (bed != null) {
                            ServiceOrderVo.BedVo bedVo = new ServiceOrderVo.BedVo();
                            bedVo.setId(bed.getId());
                            bedVo.setBedNumber(bed.getBedNumber());
                            serviceOrderVo.setBedVo(bedVo);
                        }
                    }

                    return serviceOrderVo;
                })
                .collect(Collectors.toList());

        // 构建分页响应
        return PageResponse.<ServiceOrderVo>builder()
                .page(pageNum)
                .pageSize(pageSize)
                .pages((long) page.getPages())
                .total(page.getTotal())
                .records(serviceOrderVos)
                .build();
    }

    @Override
    public PageResponse<ServiceOrderVo> searchOrders(Integer pageNum, Integer pageSize, String orderNo, String elderName, String projectName, Integer status) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);

        // 查询订单列表
        List<ServiceOrder> serviceOrders = serviceOrderMapper.selectByConditions(orderNo, elderName, projectName, status);
        Page<ServiceOrder> page = (Page<ServiceOrder>) serviceOrders;

        // 转换为VO对象
        List<ServiceOrderVo> serviceOrderVos = serviceOrders.stream()
                .map(serviceOrder -> {
                    ServiceOrderVo serviceOrderVo = new ServiceOrderVo();
                    BeanUtils.copyProperties(serviceOrder, serviceOrderVo);
                    serviceOrderVo.setStatusDesc(getStatusDesc(serviceOrder.getStatus()));

                    // 映射交易状态（兼容前端）
                    serviceOrderVo.setPaymentStatus(mapToPaymentStatus(serviceOrder.getStatus()));

                    // 初始化退款记录和护理任务对象，避免前端null引用错误
                    serviceOrderVo.setRefundRecordVo(new ServiceOrderVo.RefundRecordVo());
                    serviceOrderVo.setNursingTaskVo(new ServiceOrderVo.NursingTaskVo());

                    // 查询护理项目信息
                    NursingProject nursingProject = nursingProjectMapper.selectById(serviceOrder.getProjectId());
                    if (nursingProject != null) {
                        // 设置护理项目相关字段
                        serviceOrderVo.setProjectImage(nursingProject.getImage());
                        serviceOrderVo.setProjectUnit(nursingProject.getUnit());

                        // 设置嵌套对象（兼容前端）
                        ServiceOrderVo.NursingProjectVo nursingProjectVo = new ServiceOrderVo.NursingProjectVo();
                        nursingProjectVo.setId(nursingProject.getId());
                        nursingProjectVo.setName(nursingProject.getName());
                        nursingProjectVo.setImage(nursingProject.getImage());
                        nursingProjectVo.setUnit(nursingProject.getUnit());
                        nursingProjectVo.setPrice(nursingProject.getPrice());
                        serviceOrderVo.setNursingProjectVo(nursingProjectVo);
                    }

                    // 设置老人信息嵌套对象（兼容前端）
                    ServiceOrderVo.ElderVo elderVo = new ServiceOrderVo.ElderVo();
                    elderVo.setId(serviceOrder.getElderId());
                    elderVo.setName(serviceOrder.getElderName());
                    serviceOrderVo.setElderVo(elderVo);

                    // 查询下单人信息
                    Member member = memberMapper.selectById(serviceOrder.getUserId());
                    if (member != null) {
                        ServiceOrderVo.MemberVo memberVo = new ServiceOrderVo.MemberVo();
                        memberVo.setId(member.getId());
                        memberVo.setName(member.getName());
                        serviceOrderVo.setMemberVo(memberVo);
                    }

                    // 查询老人详细信息获取床位信息
                    Elder elder = elderMapper.selectById(serviceOrder.getElderId());
                    if (elder != null && elder.getBedId() != null) {
                        Bed bed = bedMapper.selectById(elder.getBedId());
                        if (bed != null) {
                            ServiceOrderVo.BedVo bedVo = new ServiceOrderVo.BedVo();
                            bedVo.setId(bed.getId());
                            bedVo.setBedNumber(bed.getBedNumber());
                            serviceOrderVo.setBedVo(bedVo);
                        }
                    }

                    return serviceOrderVo;
                })
                .collect(Collectors.toList());

        // 构建分页响应
        return PageResponse.<ServiceOrderVo>builder()
                .page(pageNum)
                .pageSize(pageSize)
                .pages((long) page.getPages())
                .total(page.getTotal())
                .records(serviceOrderVos)
                .build();
    }

    @Override
    public void cancelOrder(Long orderId, String reason) {
        ServiceOrder serviceOrder = serviceOrderMapper.selectById(orderId);
        if (serviceOrder == null) {
            throw new RuntimeException("订单不存在");
        }

        if (serviceOrder.getStatus() != 0 && serviceOrder.getStatus() != 1) {
            throw new RuntimeException("只有待支付或已支付的订单可以取消");
        }

        serviceOrder.setStatus(4); // 已取消
        serviceOrder.setRemark(reason);
        serviceOrder.setUpdateTime(LocalDateTime.now());

        serviceOrderMapper.updateById(serviceOrder);
    }

    @Override
    public void refundOrder(ServiceOrderDto serviceOrderDto) {
        // TODO: 实现退款逻辑，这里暂时只更新订单状态
        Long orderId = serviceOrderDto.getId();
        if (orderId == null) {
            throw new RuntimeException("订单ID不能为空");
        }

        ServiceOrder serviceOrder = serviceOrderMapper.selectById(orderId);
        if (serviceOrder == null) {
            throw new RuntimeException("订单不存在");
        }

        if (serviceOrder.getStatus() != 1) {
            throw new RuntimeException("只有已支付的订单可以退款");
        }

        serviceOrder.setStatus(4); // 已取消（暂时用取消状态）
        serviceOrder.setUpdateTime(LocalDateTime.now());

        serviceOrderMapper.updateById(serviceOrder);
    }

    /**
     * 临时支付功能 - 更新订单状态为已支付
     * @param orderId 订单ID
     */
    public void updateOrderStatusToPaid(Long orderId) {
        log.info("临时支付更新订单状态: {}", orderId);

        ServiceOrder serviceOrder = serviceOrderMapper.selectById(orderId);
        if (serviceOrder == null) {
            throw new RuntimeException("订单不存在");
        }

        if (serviceOrder.getStatus() != 0) {
            throw new RuntimeException("只有待支付的订单可以进行支付");
        }

        serviceOrder.setStatus(1); // 已支付
        serviceOrder.setUpdateTime(LocalDateTime.now());

        serviceOrderMapper.updateById(serviceOrder);
        log.info("订单状态更新成功，订单ID: {}, 状态: 已支付", orderId);
    }
    private String generateOrderNo() {
        return "ORD" + System.currentTimeMillis();
    }

    /**
     * 获取状态描述
     */
    private String getStatusDesc(Integer status) {
        switch (status) {
            case 0:
                return "待支付";
            case 1:
                return "已支付";
            case 2:
                return "服务中";
            case 3:
                return "已完成";
            case 4:
                return "已取消";
            default:
                return "未知状态";
        }
    }

    /**
     * 将订单状态映射为交易状态（兼容前端）
     */
    private Integer mapToPaymentStatus(Integer status) {
        if (status == null) {
            return null;
        }
        switch (status) {
            case 0:
                return 1; // 待支付
            case 1:
            case 2:
                return 2; // 已支付
            case 3:
                return 2; // 已完成算作已支付
            case 4:
                return 3; // 已取消
            default:
                return 1; // 默认待支付
        }
    }
}