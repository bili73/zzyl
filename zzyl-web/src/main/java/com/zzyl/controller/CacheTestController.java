package com.zzyl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存测试控制器
 */
@RestController
@RequestMapping("/test")
public class CacheTestController {

    @Autowired
    private CacheManager cacheManager;

    @GetMapping("/cache-info")
    public ResponseEntity<Map<String, Object>> getCacheInfo() {
        Map<String, Object> info = new HashMap<>();

        // 检查缓存管理器
        if (cacheManager != null) {
            info.put("cacheManagerType", cacheManager.getClass().getSimpleName());
            info.put("cacheNames", cacheManager.getCacheNames());
            info.put("cacheManager", cacheManager.toString());
        } else {
            info.put("cacheManager", "NULL - 缓存管理器未注入!");
        }

        return ResponseEntity.ok(info);
    }

    @GetMapping("/cache-test")
    public ResponseEntity<String> cacheTest() {
        return ResponseEntity.ok("缓存系统测试完成");
    }
}