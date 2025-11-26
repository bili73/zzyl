package com.zzyl.task;

import com.zzyl.mapper.VisitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 预约定时任务
 */
@Component
public class ReservationScheduledTask {

    @Autowired
    private VisitMapper visitMapper;

    /**
     * 自动过期预约
     * 每小时的1分钟和31分钟执行
     */
    @Scheduled(cron = "0 1,31 * * * ?")
    public void autoExpireReservations() {
        System.out.println("开始执行预约过期检查任务，时间：" + LocalDateTime.now());

        // 将当前时间之前的待报道预约更新为过期状态
        LocalDateTime now = LocalDateTime.now();
        int updatedCount = visitMapper.expireOverdueReservations(now);

        System.out.println("预约过期检查任务完成，更新了 " + updatedCount + " 条预约记录");
    }
}