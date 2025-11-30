package com.zzyl.controller;

import com.zzyl.base.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息前端控制器
 */
@Slf4j
@Api(tags = "消息管理")
@RestController
@RequestMapping("/message")
public class MessageController {

    /**
     * 根据读取状态统计消息数量
     * @return 消息统计数量
     */
    @GetMapping("/countByReadStatus")
    @ApiOperation(value = "根据读取状态统计消息数量",notes = "根据读取状态统计消息数量")
    public ResponseResult<Map<String, Integer>> countByReadStatus() {
        // 暂时返回空的统计数据，等后续实现了MessageService后再完善
        Map<String, Integer> countMap = new HashMap<>();
        countMap.put("unread", 0);
        countMap.put("read", 0);
        countMap.put("total", 0);
        return ResponseResult.success(countMap);
    }

}