package com.xkcoding.orm.mybatis.map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import org.junit.Test;

import java.util.Collection;
import java.util.Map;

public class MultiMapTest {
    @Test
    public void multiMapTest() {

        Multimap<Integer, String> map = ArrayListMultimap.create();
        map.put(0, "0-0");
        map.put(0, "0-0");
        map.put(0, "0-2");
        map.put(0, "0-3");
        map.put(1, "1-0");
        map.put(1, "1-1");
        map.put(1, "1-1");
        map.put(2, "2-0");
        System.out.println("\n获取map长度:\n" + map.size());

        // 获取指定值
        Collection<String> strings = map.get(1);
        System.out.println("\n获取指定值:\n" + strings);

        // Immutable不可见集合  获取Immutable中不重复的集合
        System.out.println("\n获取map中不重复的集合:\n" + ImmutableSet.copyOf(strings));

        // 遍历 Mutlimap中values所有元素
        System.out.println("\n遍历 Mutlimap中values所有元素:");
        for (String value : map.values()) {
            System.out.println(value);
        }

        // 遍历所有元素
        System.out.println("\n遍历所有元素:");
        for (Map.Entry<Integer, String> e : map.entries()) {
            System.out.println(e.getKey() +": "+ e.getValue());
        }

        // 移除指定的一个元素
        map.remove(1, "1-1");
        System.out.println("\n移除指定的一个元素:\n" + map.get(1));

        // 移除所有key为1的元素
        map.removeAll(1);
        System.out.println("\n移除所有key为1的元素:\n" + map.get(1));


        map.put(1, "1-0");
        map.put(1, "1-1");
        map.put(1, "1-1");
    }
}
