package com.jhdz.http;

import com.alibaba.fastjson.JSONObject;
import com.jhdz.http.utils.HttpClientUtil;
import com.jhdz.http.utils.HttpURLConnectionUtil;
import org.junit.Test;

import java.util.HashMap;

public class httpTest {
    @Test
    public void httpURLConnectionTestGet(){
        String s = HttpURLConnectionUtil.doGet("https://ifconfig.me/ip");
        System.out.println(s);
    }

    @Test
    public void httpURLConnectionTestPost(){
        String s = HttpURLConnectionUtil.doPost("","");
        System.out.println(s);
    }

    @Test
    public void httpClientTestGet(){
        String s = HttpClientUtil.doGet("https://ifconfig.me/ip","UTF-8");
        System.out.println(s);
    }

    @Test
    public void httpClientTestPost(){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("commentId", 1);
        JSONObject jsonObject = new JSONObject(hashMap);
        System.out.println(jsonObject);
        String s = HttpClientUtil.doPost("https://ifconfig.me/ip",jsonObject);
        System.out.println(s);
    }
}
