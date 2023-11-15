package com.xkcoding.orm.mybatis;

import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTest {

    /**
     * Callable
     * Lambda表达式版
     */
    @Test
    public void callableTest() {
        ArrayList<Callable<String>> callables = new ArrayList<>();
        Callable callable = () -> get(1);
        callables.add(callable);
        String s = "";
        ExecutorService executorService = Executors.newCachedThreadPool();
        try {
            s = executorService.invokeAny(callables);
            System.out.println(s);
        } catch (Exception e){
            e.getMessage();
        } finally {
            executorService.shutdown();
        }
    }

    /**
     * Callable
     * 普通匿名内部类版
     */
    @Test
    public void callableTest2(){
        ArrayList<Callable<String>> callables = new ArrayList<>();
        Callable callable = new Callable<String>() {
            @Override
            public String call(){
                System.out.println("1");
                return get(1);
            }
        };
        callables.add(callable);
        ExecutorService executorService = Executors.newCachedThreadPool();

        try {
            String s = executorService.invokeAny(callables);
            System.out.println(s);
        }catch (Exception e) {
            e.getMessage();
        } finally {
            executorService.shutdown();
        }

    }


    public String get(Integer c){
        return "what?" + c;
    }
}

