package com.xkcoding.orm.mybatis.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CallableTest2 {
    public static void main(String[] args) {

        List<Callable<String>> callables = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            callables.add(() -> {
                for (int j = 0; j < 100; j++) {
                    System.out.println("线程:" + finalI + "--running" + j);
                }
                return "complete" + finalI;
            });
        }
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            String s = executorService.invokeAny(callables);
            // 即使已经有线程已经执行完毕，剩余线程仍然会继续执行，只是这里已经返回最先执行好的值了
            System.out.println(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}
