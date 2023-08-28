package com.xkcoding.orm.mybatis.thread;

import com.xkcoding.orm.mybatis.thread.domin.MyThread;
import org.junit.Test;

public class ThreadTest {

    // 使用@Test进行测试时，程序无法执行完整
    public static void main(String[] args) {
        MyThread myThread1 = new MyThread();
        MyThread myThread2 = new MyThread();
        MyThread myThread3 = new MyThread();

        myThread1.setName("a");
        myThread2.setName("b");
        myThread3.setName("c");

        myThread1.start();
        myThread2.start();
        myThread3.start();
    }

}
