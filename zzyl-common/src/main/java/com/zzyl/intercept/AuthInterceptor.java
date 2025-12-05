package com.zzyl.intercept;

import cn.hutool.core.map.MapUtil;
import com.zzyl.constant.Constants;
import com.zzyl.enums.BasicEnum;
import com.zzyl.exception.BaseException;
import com.zzyl.properties.JwtTokenManagerProperties;
import com.zzyl.utils.JwtUtil;
import com.zzyl.utils.ObjectUtil;
import com.zzyl.utils.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenManagerProperties jwtTokenManagerProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法就放行，比如跨域验证请求，静态资源等不需要身份效验的请求
        if (!(handler instanceof HandlerMethod)) {
            log.debug("不是方法处理器，直接放行: {}", request.getRequestURI());
            return true;
        }

        // 从请求头中获取token
        String token = request.getHeader("Authorization");
        log.debug("请求路径: {}, Authorization: {}", request.getRequestURI(), token);

        // 如果没有token，返回401未授权
        if (ObjectUtil.isEmpty(token)) {
            log.warn("请求未携带Authorization token: {}", request.getRequestURI());
            throw new BaseException(BasicEnum.SECURITY_ACCESSDENIED_FAIL);
        }

        try {
            // 解析JWT token
            String secretKey = jwtTokenManagerProperties.getBase64EncodedSecretKey();
            Map<String, Object> claims = JwtUtil.parseJWT(secretKey, token);

            if (MapUtil.isEmpty(claims)) {
                log.warn("JWT token解析失败: {}", request.getRequestURI());
                throw new BaseException(BasicEnum.SECURITY_ACCESSDENIED_FAIL);
            }

            // 验证token中的用户ID
            Object userIdObj = claims.get(Constants.JWT_USERID);
            if (userIdObj == null) {
                log.warn("JWT token中无用户ID: {}", request.getRequestURI());
                throw new BaseException(BasicEnum.SECURITY_ACCESSDENIED_FAIL);
            }

            // 将用户ID存入ThreadLocal
            Long userId = Long.valueOf(userIdObj.toString());
            UserThreadLocal.set(userId);

            log.debug("JWT token验证成功: userId={}, requestUri={}", userId, request.getRequestURI());
            return true;

        } catch (Exception e) {
            log.error("JWT token验证失败: requestUri={}, error={}", request.getRequestURI(), e.getMessage());
            throw new BaseException(BasicEnum.SECURITY_ACCESSDENIED_FAIL);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清理ThreadLocal，防止内存泄漏
        UserThreadLocal.remove();
    }
}