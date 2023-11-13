package com.xkcoding.orm.mybatis.annotation;

import org.junit.Test;

public class AnnotationTest {

    // @Deprecated标记方法已过期，不推荐使用
    @Test
    public void deprecatedTest(){
        Hero hero = new Hero();
        hero.hello();
        hero.say();
    }

    // @SuppressWarnings("deprecation") 无视方法是否过期，正常显示方法
    @Test
    @SuppressWarnings("deprecation")
    public void suppressWarningsTest(){
        Hero hero = new Hero();
        hero.hello();
        hero.say();
    }
}
