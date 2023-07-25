package com.xkcoding.orm.mybatis.thread;

import org.junit.Test;

public class ThreadTest {

    @Test
    public void ThreadTest1() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {

                }
            }
        };
    }
}
