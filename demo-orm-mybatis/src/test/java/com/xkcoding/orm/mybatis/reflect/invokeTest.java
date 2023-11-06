package com.xkcoding.orm.mybatis.reflect;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkcoding.orm.mybatis.reflect.util.SpringBeanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class invokeTest {

    final static Map<String, Object> map = new HashMap<>();
    {
        map.put("id", 1);
        map.put("name", "liHua");
        map.put("age", 16);
        map.put("now", new Date());
    }
    @Test
    public void classForName() throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, ParseException {
        String entityName = "DataEntity";
        Class<?> className = Class.forName("com.xkcoding.orm.mybatis.entity." + entityName);

        Object instance = className.newInstance();
        Method[] methods = instance.getClass().getMethods();
        for (String key : map.keySet()){
            Object value = map.get(key);
            String methodName = "set" + upperFirstCase(key);
            for (Method method : methods) {
                if (method.getName().equals(methodName) ) {
                    if (method.getParameterTypes()[0].equals(String.class)){
                        if (ObjectUtils.isEmpty(value)){
                            value = " ";
                        }
                        method.invoke(instance, value);
                    } else if(method.getParameterTypes()[0].equals(Date.class)) {
                        if (ObjectUtils.isEmpty(value)){
                            continue;
                        }

                        SimpleDateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                        method.invoke(instance, df.parse(value.toString()));
                    } else if (method.getParameterTypes()[0].equals(Integer.class)||method.getParameterTypes()[0].equals(int.class)) {
                        if (org.springframework.util.ObjectUtils.isEmpty(value)){
                            continue;
                        }
                        method.invoke(instance, Integer.parseInt(value.toString()));
                    } else if (method.getParameterTypes()[0].equals(Long.class)||method.getParameterTypes()[0].equals(long.class)) {
                        if (org.springframework.util.ObjectUtils.isEmpty(value)){
                            continue;
                        }
                        method.invoke(instance, Long.parseLong(value.toString()));
                    } else if (method.getParameterTypes()[0].equals(Double.class)||method.getParameterTypes()[0].equals(double.class)) {
                        if (org.springframework.util.ObjectUtils.isEmpty(value)){
                            continue;
                        }
                        method.invoke(instance, Double.parseDouble(value.toString()));
                    } else if (method.getParameterTypes()[0].equals(Boolean.class)||method.getParameterTypes()[0].equals(boolean.class)) {
                        if (org.springframework.util.ObjectUtils.isEmpty(value)){
                            continue;
                        }
                        method.invoke(instance, Boolean.parseBoolean(value.toString()));
                    }
                    break;
                }
            }
        }
        System.out.println(instance.toString());
        // 进行后续的数据库持久化保存
//        String newTable= lowerFirstCase(entityName+"ServiceImpl");
//        ServiceImpl service = (ServiceImpl) SpringBeanUtils.getBeanByString(newTable);
//        service.save(instance);
    }

    public String lowerFirstCase(String lower){
        char[] chars = lower.toCharArray();
        chars[0] +=32;
        return  String.valueOf(chars);
    }
    public String upperFirstCase(String lower){
        char[] chars = lower.toCharArray();
        chars[0] -=32;
        return  String.valueOf(chars);
    }
}
