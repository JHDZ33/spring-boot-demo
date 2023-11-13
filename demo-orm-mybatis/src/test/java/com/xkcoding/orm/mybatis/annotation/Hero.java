package com.xkcoding.orm.mybatis.annotation;

public class Hero {
    @Deprecated
    public void hello(){
        System.out.println("hello");
    }

    public void say(){
        System.out.println("nothingToSay");
    }
}
