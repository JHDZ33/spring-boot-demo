package com.xkcoding.orm.mybatis.thread.domin;

import java.util.concurrent.Callable;

public class MyCallable implements Callable {
    @Override
    public String call() throws Exception {
        return "running";
    }
}
