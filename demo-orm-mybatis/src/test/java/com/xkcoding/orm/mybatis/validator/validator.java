package com.xkcoding.orm.mybatis.validator;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * 数据格式验证相关
 */
public class validator {

    /**
     * 邮箱格式验证
     */
    @Test
    public void emailTest() {
        String email = "jhdz112435@outlook.com";
        if (email == null || email.length() < 1 || email.length() > 256) {
            System.out.println(false);;
        }
        String reg="^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        Pattern pattern = Pattern.compile(reg);
        System.out.println(pattern.matcher(email).matches());
    }
    /**
     * 手机格式验证
     */
    @Test
    public void mobileTest() {
        String mobile = "18499999999";
        if (mobile == null || mobile.length() != 11) {
            System.out.println(false);
        }
        String reg="^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        Pattern pattern = Pattern.compile(reg);
        System.out.println(pattern.matcher(mobile).matches());
    }

    /**
     * 车牌格式验证
     */
    @Test
    public void plateNoTest() {
        /**
         * 对于新能源车而言第三部分的第一位并不是只能使用D/F，根据《GA36-2018》当D/F启用数量饱和后，可以启用其他字母替代。
         * 纯电：D、A、B、C、E，但是优先启用D，待到D号段耗竭方可慢慢逐步启用A、B、C、E。
         * 非纯：F、G、H、J、K，但是优先启用F，待到F号段耗竭方可慢慢逐步启用G、H、J、K。
         */
        String plateNo = "浙ABB8888";
        String reg="^(([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-HJ-NP-Z](([0-9]{5}[ABCDEFGHJK])|([ABCDEFGHJK]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-HJ-NP-Z][A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳使领]))$";
        Pattern pattern = Pattern.compile(reg);
        boolean matches = pattern.matcher(plateNo).matches();
        System.out.println(matches);
    }



}
