package com.xkcoding.orm.mybatis.fastjson;

import com.xkcoding.orm.mybatis.entity.DataResultEntity;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class TypeReferenceTest {
    public static class IntMap extends HashMap<String, Integer> {}

    @Test
    public void test1() {
        IntMap intMap = new IntMap();
        System.out.println("getSuperclass:" + intMap.getClass().getSuperclass());
        System.out.println("getGenericSuperclass:" + intMap.getClass().getGenericSuperclass());
        Type type = intMap.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType p = (ParameterizedType)type;
            for (Type t : p.getActualTypeArguments()) {
                System.out.println(t);
            }
        }
    }

    @Test
    public void test2() {
        Map<String, Integer> intMap = new HashMap<>();
        Class<? extends Map> aClass = intMap.getClass();
        System.out.println("\ngetSuperclass:" + intMap.getClass().getSuperclass());// getSuperclass:class java.util.AbstractMap
        System.out.println("getGenericSuperclass:" + intMap.getClass().getGenericSuperclass());// getGenericSuperclass:java.util.AbstractMap<K, V>
        Type type = intMap.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType p = (ParameterizedType)type;
            for (Type t : p.getActualTypeArguments()) {
                System.out.println(t);
            }
        }
    }

    @Test
    public void test3() {
        Map<String, Integer> intMap = new HashMap<String, Integer>(){};
        Class<? extends Map> aClass = intMap.getClass();
        System.out.println("\ngetSuperclass:" + intMap.getClass().getSuperclass()); // getSuperclass:class java.util.HashMap
        System.out.println("getGenericSuperclass:" + intMap.getClass().getGenericSuperclass()); // getGenericSuperclass:java.util.HashMap<java.lang.String, java.lang.Integer>
        Type type = intMap.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType p = (ParameterizedType)type;
            for (Type t : p.getActualTypeArguments()) {
                System.out.println(t);
            }
        }
    }

    @Test
    public void test4() {
        DataResultEntity dataResultEntity = new DataResultEntity();
        System.out.println("\ngetSuperclass:" + dataResultEntity.getClass().getSuperclass());
        System.out.println("getGenericSuperclass:" + dataResultEntity.getClass().getGenericSuperclass());
        Type type = dataResultEntity.getClass().getGenericSuperclass();
        Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        System.out.println(actualTypeArguments);
        if (type instanceof ParameterizedType) {
            ParameterizedType p = (ParameterizedType)type;
            for (Type t : p.getActualTypeArguments()) {
                System.out.println(t);
            }
        }
    }
}
