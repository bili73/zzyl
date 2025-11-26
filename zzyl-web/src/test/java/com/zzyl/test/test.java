package com.zzyl.test;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class test {
    @Test
    public void testPost(){
        String url = "http://localhost:9995/nursing-project/add";  // 修正API路径
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "护理项目测试1");
        paramMap.put("orderNo", 1);
        paramMap.put("unit", "次");
        paramMap.put("price", 10.00);
        paramMap.put("image", "https://yjy-slwl-oss.oss-cn-hangzhou.aliyuncs.com/ae7cf766-fb7b-49ff-a73c-c86c25f280e1.png");
        paramMap.put("nursingRequirement", "无特殊要求");
        paramMap.put("status", 1);

        HttpResponse response = HttpUtil.createRequest(Method.POST, url).body(JSONUtil.toJsonStr(paramMap)).execute();
        if(response.getStatus() == 200){
            System.out.println("请求成功！");
            System.out.println(response.body());
        } else {
            System.out.println("请求失败，状态码：" + response.getStatus());
            System.out.println("响应内容：" + response.body());
        }
    }

    @Test
    public void testWeather() {
        String url = "https://getweather.market.alicloudapi.com/lundear/weather1d";
        String appcode = "bf221f82cbc34020b353174a835e2219";
        Map<String, Object> querys = new HashMap<String, Object>();
        querys.put("areaCode", "110000");

        HttpResponse response = HttpUtil.createRequest(Method.GET, url)
                .header("Authorization", "APPCODE " + appcode)
                .form(querys)
                .execute();
        if (response.getStatus() == 200) {
            System.out.println("请求成功！");
            System.out.println(response.body());
        }

    }

}
