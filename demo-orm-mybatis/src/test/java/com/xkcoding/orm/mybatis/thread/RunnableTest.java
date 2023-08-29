package com.xkcoding.orm.mybatis.thread;

import com.xkcoding.orm.mybatis.thread.domin.MyRunnable;

public class RunnableTest {
    public static void main(String[] args) {

        // runnable和thread效果一样，只是thread需要继承，由于java只能单继承所以有时用不了
        // runnable适合多个相同的程序代码去处理同一资源的情况
        MyRunnable myRunnable = new MyRunnable();
        // 第二个参数是线程名
        Thread thread1 = new Thread(myRunnable, "a");
        Thread thread2 = new Thread(myRunnable, "b");
        Thread thread3 = new Thread(myRunnable, "c");

        thread1.start();
        try {
            thread1.join(); //// 等待thread1执行完才会轮到thread2，thread3抢
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread2.start();
        thread3.start();
    }
}
