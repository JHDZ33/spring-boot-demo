package com.xkcoding.orm.mybatis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用于方法上的注解，目的是使返回字符串的方法返回值全部大写并拼接count次content字符串
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface upperAndJoint {
    int count();
    String content();
}
