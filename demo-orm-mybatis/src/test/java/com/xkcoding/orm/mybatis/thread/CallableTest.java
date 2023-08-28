package com.xkcoding.orm.mybatis.thread;

import com.xkcoding.orm.mybatis.thread.domin.MyCallable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableTest {
    public static void main(String[] args) {
        FutureTask<String> futureTask = new FutureTask<String>(new MyCallable());
        new Thread(futureTask).start();
        try {
            String s = futureTask.get();
            System.out.println(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
