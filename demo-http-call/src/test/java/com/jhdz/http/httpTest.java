package com.jhdz.http;

import com.jhdz.http.utils.HttpURLConnectionUtil;
import org.junit.Test;

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
}
