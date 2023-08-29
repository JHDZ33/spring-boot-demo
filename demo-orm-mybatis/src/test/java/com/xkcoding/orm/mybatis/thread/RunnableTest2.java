package com.xkcoding.orm.mybatis.thread;

import org.junit.Test;

public class RunnableTest2 {


    @Test
    public void threadStatus() {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                methodTest();
            }
        },"a");
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                methodTest();
            }
        },"b");

        thread1.start();
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread2.start();

        System.out.println(thread1.getState());
        System.out.println(thread2.getState());
    }


    private synchronized void methodTest() {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
