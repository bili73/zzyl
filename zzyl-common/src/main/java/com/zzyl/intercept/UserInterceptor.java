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
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenManagerProperties jwtTokenManagerProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法就放行，比如跨域验证请求，静态资源等不需要身份效验的请求
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
       //获取header中的token
        String token = request.getHeader(Constants.USER_TOKEN);

        log.info("JWT认证调试信息:");
        log.info("- 请求URL: {}", request.getRequestURL());
        log.info("- 请求方法: {}", request.getMethod());
        log.info("- Header中的token: {}", token);
        log.info("- Constants.USER_TOKEN: {}", Constants.USER_TOKEN);

        //判断token是否为空
        if(ObjectUtil.isEmpty(token)){
            log.error("Token为空，拒绝访问");
            throw new BaseException(BasicEnum.SECURITY_ACCESSDENIED_FAIL);
        }

        //解析token
        Map<String, Object> claims = null;
        try {
            claims = JwtUtil.parseJWT(jwtTokenManagerProperties.getBase64EncodedSecretKey(), token);
            log.info("- JWT解析成功: {}", claims);
        } catch (Exception e) {
            log.error("JWT解析失败: {}", e.getMessage());
            throw new BaseException(BasicEnum.SECURITY_ACCESSDENIED_FAIL);
        }

        //判断claims是否为空
        if(ObjectUtil.isEmpty(claims)){
            log.error("JWT claims为空，拒绝访问");
            throw new BaseException(BasicEnum.SECURITY_ACCESSDENIED_FAIL);
        }

        //获取用户id
        Long userId = MapUtil.get(claims, Constants.JWT_USERID, Long.class);
        log.info("- 从JWT中获取的用户ID: {}", userId);

        //判断userId是否为空
        if(ObjectUtil.isEmpty(userId)){
            log.error("用户ID为空，拒绝访问");
            throw new BaseException(BasicEnum.SECURITY_ACCESSDENIED_FAIL);
        }

        //存入当前线程中
        UserThreadLocal.set(userId);
        log.info("- 用户ID已存入ThreadLocal: {}", UserThreadLocal.getUserId());

        //放行
        return true;
    }

    /**
     * 拦截响应
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
       //响应结束，释放线程变量
        UserThreadLocal.remove();
    }
}