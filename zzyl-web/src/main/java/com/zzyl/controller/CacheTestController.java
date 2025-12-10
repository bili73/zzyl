package com.zzyl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 缓存测试控制器
 */
@RestController
@RequestMapping("/test")
public class CacheTestController {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

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

    /**
     * 清除用户权限缓存 - 无需认证的公开接口
     * @param userId 用户ID（可选，不传则清除所有权限缓存）
     * @return 清除结果
     */
    @GetMapping("/public-clear-cache")
    public ResponseEntity<Map<String, Object>> publicClearUserPermissionCache(@RequestParam(required = false) Long userId) {
        Map<String, Object> result = new HashMap<>();

        try {
            if (userId != null) {
                // 清除指定用户的权限缓存
                String cacheKey = "user:public_access_urls:" + userId;
                Boolean deleted = redisTemplate.delete(cacheKey);
                result.put("userId", userId);
                result.put("cacheKey", cacheKey);
                result.put("deleted", deleted);
                result.put("message", deleted ? "用户权限缓存清除成功" : "用户权限缓存不存在或清除失败");
            } else {
                // 清除所有用户权限缓存
                String pattern = "user:public_access_urls:*";
                Set<String> keys = redisTemplate.keys(pattern);
                int deletedCount = 0;

                if (keys != null && !keys.isEmpty()) {
                    for (String key : keys) {
                        Boolean deleted = redisTemplate.delete(key);
                        if (deleted) {
                            deletedCount++;
                        }
                    }
                }

                result.put("pattern", pattern);
                result.put("foundKeys", keys != null ? keys.size() : 0);
                result.put("deletedCount", deletedCount);
                result.put("message", "清除了 " + deletedCount + " 个用户权限缓存");
            }

            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
            result.put("message", "清除缓存失败: " + e.getMessage());
        }

        return ResponseEntity.ok(result);
    }

    /**
     * 清除用户权限缓存
     * @param userId 用户ID（可选，不传则清除所有权限缓存）
     * @return 清除结果
     */
    @GetMapping("/clear-user-cache")
    public ResponseEntity<Map<String, Object>> clearUserPermissionCache(@RequestParam(required = false) Long userId) {
        Map<String, Object> result = new HashMap<>();

        try {
            if (userId != null) {
                // 清除指定用户的权限缓存
                String cacheKey = "user:public_access_urls:" + userId;
                Boolean deleted = redisTemplate.delete(cacheKey);
                result.put("userId", userId);
                result.put("cacheKey", cacheKey);
                result.put("deleted", deleted);
                result.put("message", deleted ? "用户权限缓存清除成功" : "用户权限缓存不存在或清除失败");
            } else {
                // 清除所有用户权限缓存
                String pattern = "user:public_access_urls:*";
                Set<String> keys = redisTemplate.keys(pattern);
                int deletedCount = 0;

                if (keys != null && !keys.isEmpty()) {
                    for (String key : keys) {
                        Boolean deleted = redisTemplate.delete(key);
                        if (deleted) {
                            deletedCount++;
                        }
                    }
                }

                result.put("pattern", pattern);
                result.put("foundKeys", keys != null ? keys.size() : 0);
                result.put("deletedCount", deletedCount);
                result.put("message", "清除了 " + deletedCount + " 个用户权限缓存");
            }

            result.put("success", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
            result.put("message", "清除缓存失败: " + e.getMessage());
        }

        return ResponseEntity.ok(result);
    }
}