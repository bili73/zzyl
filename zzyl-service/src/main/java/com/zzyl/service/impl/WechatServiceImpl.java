package com.zzyl.service.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zzyl.service.WechatService;
import com.zzyl.utils.ObjectUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WechatServiceImpl implements WechatService {

    // 登录
    private static final String REQUEST_URL = "https://api.weixin.qq.com/sns/jscode2session?grant_type=authorization_code";

    // 获取token
    private static final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";

    // 获取手机号
    private static final String PHONE_REQUEST_URL = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=";

    @Value("${zzyl.wechat.appId}")
    private String appId;

    @Value("${zzyl.wechat.appSecret}")
    private String secret;

    /**
     * 获取openid
     * @param code  登录凭证
     * @return
     */
    @Override
    public String getOpenid(String code) {
        Map<String, Object> requestUrlParam = getAppConfig();
        requestUrlParam.put("js_code",code);
        String result = HttpUtil.get(REQUEST_URL, requestUrlParam);
        //反序列化
        JSONObject jsonObject = JSONUtil.parseObj(result);
        // 若code不正确，则获取不到openid，响应失败
        if (ObjectUtil.isNotEmpty(jsonObject.getInt("errcode"))) {
            throw new RuntimeException(jsonObject.getStr("errmsg"));
        }
        return jsonObject.getStr("openid");
    }

    /**
     * 获取手机号
     * @param code  手机号凭证
     * @return
     */
    @Override
    public String getPhone(String code) {
        //获取access_token
        String token = getToken();
        //拼接请求路径
        String url = PHONE_REQUEST_URL + token;
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        String result = HttpUtil.post(url, JSONUtil.toJsonStr(param));
        JSONObject jsonObject = JSONUtil.parseObj(result);
        if (jsonObject.getInt("errcode") != 0) {
            //若code不正确，则获取不到phone，响应失败
            throw new RuntimeException(jsonObject.getStr("errmsg"));

        }
        return jsonObject.getJSONObject("phone_info").getStr("purePhoneNumber");
    }

    private String getToken() {
        Map<String, Object> requestUrlParam = getAppConfig();
        String result = HttpUtil.get(TOKEN_URL, requestUrlParam);
        //解析
        JSONObject jsonObject = JSONUtil.parseObj(result);
        //如果code不正确，则失败
        if(ObjectUtil.isNotEmpty(jsonObject.getInt("errcode"))){
            throw new RuntimeException(jsonObject.getStr("errmsg"));
        }
        return jsonObject.getStr("access_token");
    }

    /**
     * 封装公共参数
     * @return
     */
    private Map<String, Object> getAppConfig() {

        Map<String, Object> requestUrlParam = new HashMap<>();
        requestUrlParam.put("appid",appId);
        requestUrlParam.put("secret",secret);
        return requestUrlParam;
    }
}
