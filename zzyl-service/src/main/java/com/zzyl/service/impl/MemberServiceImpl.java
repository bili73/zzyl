package com.zzyl.service.impl;

import com.zzyl.constant.Constants;
import com.zzyl.dto.UserLoginRequestDto;
import com.zzyl.entity.Member;
import com.zzyl.mapper.MemberMapper;
import com.zzyl.properties.JwtTokenManagerProperties;
import com.zzyl.service.MemberService;
import com.zzyl.service.WechatService;
import com.zzyl.utils.JwtUtil;
import com.zzyl.utils.ObjectUtil;
import com.zzyl.utils.StringUtils;
import com.zzyl.vo.LoginVo;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private WechatService wechatService;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private JwtTokenManagerProperties jwtTokenManagerProperties;

    static ArrayList DEFAULT_NICKNAME_PREFIX = Lists.newArrayList(
            "生活更美好",
            "大桔大利",
            "日富一日",
            "好柿开花",
            "柿柿如意",
            "一椰暴富",
            "大柚所为",
            "杨梅吐气",
            "天生荔枝"
    );



    /**
     * 小程序登录逻辑
     * @param userLoginRequestDto
     * @return
     */
    @Override
    public LoginVo login(UserLoginRequestDto userLoginRequestDto) {
        //1.调用微信api，根据code获取openid
        String openId = wechatService.getOpenid(userLoginRequestDto.getCode());
        //2.根据openid查询用户
        Member member = memberMapper.getByOpenId(openId);
        //3.如果用户不存在，则新增用户
        if (member == null) {
            member = Member.builder().openId(openId).build();
        }
        //4.调用微信api，根据phoneCode获取手机号
        String phone = wechatService.getPhone(userLoginRequestDto.getPhoneCode());

        //5.保存或者修改用户
        saveOrUpdate(member, phone);
        //6.将用户id存入token，并返回
        Map<String,Object> claims = new HashMap<>();
        claims.put(Constants.JWT_USERID, member.getId());
        claims.put(Constants.JWT_USERNAME, member.getName());
        String token = JwtUtil.createJWT(jwtTokenManagerProperties.getBase64EncodedSecretKey(), jwtTokenManagerProperties.getTtl(), claims);
        return LoginVo.builder().token(token).nickName(member.getName()).build();
    }

    /**
     * 保存或修改用户
     * @param member
     * @param phone
     */
    private void saveOrUpdate(Member member, String phone) {
        //1.判断手机号与数据库中的是否一致
        if(ObjectUtil.notEqual(member.getPhone(), phone)){
            member.setPhone(phone);
        }
         //2.判断id是否存在
        if(ObjectUtil.isNotEmpty(member.getId())){
            memberMapper.update(member);
            return;
        }
        //3.保存新用户
        //设置昵称
        String nickName = DEFAULT_NICKNAME_PREFIX.get((int)(Math.random() * DEFAULT_NICKNAME_PREFIX.size())).toString()
                + StringUtils.substring(member.getPhone(),7);
        member.setName(nickName);
        memberMapper.save(member);


    }
}
