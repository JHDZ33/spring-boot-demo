package com.xkcoding.orm.mybatis.reflect;

import com.xkcoding.orm.mybatis.reflect.util.FunctionUtils;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

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
            String result = (String) methodFactory(methodName, args, params);
            System.out.println("输出结果" + result);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    public static Object methodFactory(String methodName, Class<?>[] args, List param) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        FunctionUtils functionUtils = new FunctionUtils();
        Method method = functionUtils.getClass().getMethod(methodName, args);
        Object invoke = method.invoke(methodName, param.toArray());
        System.out.println("调用的方法:" + methodName);
        return invoke;
    }

    @Test
    public void methodStringTest() {
        String argTypes = getArgTypes("myMethod('abc', 123, now('yyyy-MM-dd HH:mm:ss'), dateToTimestamp('yyyy-MM-dd HH:mm:ss', now('yyyy-MM-dd HH:mm:ss')))");
        System.out.println(argTypes.toString());
    }

    private String getArgTypes(String code) {
        // 按照左括号分割获取方法名和参数
        String[] tokens = new String[2];
        tokens[0] = code.substring(0, code.indexOf('('));
        tokens[1] = code.substring(code.indexOf('(') + 1);
        String methodName = tokens[0].trim();
        String argString = tokens[1].substring(0, tokens[1].length() - 1).trim();
        // 按照逗号分割获取参数类型
        String[] argTypes = Arrays.stream(split(argString))
            .map(String::trim)
            .map(arg -> {
                if (arg.matches("^'.*'$")) {
                    return "String";
                } else if (arg.matches("^\\d+$")) {
                    return "int";
                } else if (arg.matches("^\\d+\\.\\d+$")) {
                    return "double";
                } else if (arg.matches("^\\d+[Ll]$")) {
                    return "long";
                } else if (arg.matches("^\\w+\\(.*\\)$")) {
                    // 如果参数是一个方法，递归处理
                    return getArgTypes(arg);
                } else {
                    return "Object"; // 其他类型使用Object
                }
            })
            .toArray(String[]::new);
        Map<Object, Object> resultMap = new HashMap<>();
        resultMap.put("Method", methodName);
        resultMap.put("Argument", Arrays.deepToString(argTypes));
        return resultMap.toString();
    }

    private String[] split(String str) {
        List<String> parts = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        int depth = 0; // 记录括号的深度
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == ',' && depth == 0) { // 没有嵌套括号，直接分割
                parts.add(builder.toString().trim());
                builder = new StringBuilder();
            } else {
                builder.append(c);
                if (c == '(') {
                    depth++;
                } else if (c == ')') {
                    depth--;
                }
            }
        }
        parts.add(builder.toString().trim());
        return parts.toArray(new String[0]);
    }

    @Test
    public void test1() {
        String str = "abc, 123, now('yyyy-MM-dd HH:mm:ss', 'foo,bar'), dateToTimestamp('yyyy-MM-dd HH:mm:ss', now('yyyy-MM-dd HH:mm:ss'))";
        String[] arr = split(str);
        for (String s : arr) {
            System.out.println(s);
        }
    }
}
