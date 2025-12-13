package com.zzyl.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.json.JSONUtil;
import com.zzyl.constant.CacheConstants;
import com.zzyl.constant.SuperConstant;
import com.zzyl.dto.LoginDto;
import com.zzyl.entity.Resource;
import com.zzyl.entity.User;
import com.zzyl.enums.BasicEnum;
import com.zzyl.exception.BaseException;
import com.zzyl.mapper.ResourceMapper;
import com.zzyl.mapper.UserMapper;
import com.zzyl.properties.JwtTokenManagerProperties;
import com.zzyl.properties.SecurityConfigProperties;
import com.zzyl.service.LoginService;
import com.zzyl.utils.JwtUtil;
import com.zzyl.utils.ObjectUtil;
import com.zzyl.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private JwtTokenManagerProperties jwtTokenManagerProperties;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private SecurityConfigProperties securityConfigProperties;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录
     * @param loginDto
     * @return
     */
    @Override
    public UserVo login(LoginDto loginDto) {
        //验证用户是否合法
        User user = userMapper.selectByUsername(loginDto.getUsername());
        //用户不存在或者用户状态未启用，则返回401，重新登录
        if(ObjectUtil.isEmpty(user) || user.getDataState().equals(SuperConstant.DATA_STATE_1)){
            throw new BaseException(BasicEnum.LOGIN_FAIL);
        }

        //检查密码是否正确
        if(!checkPassword(loginDto.getPassword(),user.getPassword())){
            throw new BaseException(BasicEnum.LOGIN_FAIL);
        }

        //对象转换
        UserVo userVo = BeanUtil.toBean(user, UserVo.class);

        //过滤敏感数据
        userVo.setPassword("");

        //获取当前用户对应的资源URL列表
        List<String> urlList = resourceMapper.selectUrlListByUserId(userVo.getId());

        //获取白名单url列表
        List<String> publicAccessUrls = securityConfigProperties.getPublicAccessUrls();
        urlList.addAll(publicAccessUrls);

        //确保用户可以访问菜单接口（重要：菜单接口需要JWT验证但不需要特殊权限）
        urlList.add("GET/resource/menus");

        //生成token
        //把数据存入map
        Map<String,Object> claims = new HashMap<>();
        claims.put("currentUser", JSONUtil.toJsonStr(userVo));
        //创建token
        String token = JwtUtil.createJWT(jwtTokenManagerProperties.getBase64EncodedSecretKey()
                , jwtTokenManagerProperties.getTtl(), claims);
        //过期时间
        int ttl = jwtTokenManagerProperties.getTtl() / 1000;

        //存储到redis
        redisTemplate.opsForValue().set(CacheConstants.PUBLIC_ACCESS_URLS +userVo.getId(),JSONUtil.toJsonStr(urlList),
        ttl, TimeUnit.SECONDS);

        userVo.setUserToken(token);

        return userVo;
    }

    /**
     * 检查密码是否正确
     * @param password
     * @param dbPassword
     * @return
     */
    private boolean checkPassword(String password, String dbPassword) {
        return BCrypt.checkpw(password, dbPassword);
    }
}