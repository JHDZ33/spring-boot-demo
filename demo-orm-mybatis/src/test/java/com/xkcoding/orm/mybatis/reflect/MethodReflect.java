package com.xkcoding.orm.mybatis.reflect;

import com.xkcoding.orm.mybatis.reflect.util.FunctionUtils;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class MethodReflect {

    @Test
    public void method() {
        try {
            // TODO 以下参数就从传来的字符串中去找
            // 从字符串中获取对应的方法和参数
            String methodName = "nowFormat";
            // 在此输入参数类型
            Class<?>[] args = {String.class};
            // 输入参数
            LinkedList<Object> params = new LinkedList<>();
            params.add("yyyy-MM-dd HH: mm: ss");
            String result = (String)methodFactory(methodName, args, params);
            System.out.println("输出结果" + result);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public Object methodFactory(String methodName, Class<?>[] args, List param) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        FunctionUtils functionUtils = new FunctionUtils();
        Method method = functionUtils.getClass().getMethod(methodName, args);
        Object invoke = method.invoke(methodName,param.toArray());
        System.out.println("调用的方法:" + methodName);
        return invoke;
    }
}
