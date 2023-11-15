package com.xkcoding.orm.mybatis.array;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayTest {

    @Test
    public void arrayToListTest() {
        int[] arr = new int[4];
        arr[0] = 1;
        // Arrays.stream(arr).boxed() 将一个基础类型的数组（例如 int[]）转换为一个流。
        Integer[] integers = Arrays.stream(arr).boxed().toArray(Integer[]::new);
        // Arrays.asList()方法实际上返回的是 Object[] 类型的列表,无法解析基本数据类型，需要实现转换成Integer[]
        List<Integer> list = Arrays.asList(integers);
        System.out.println(list); // prints "[1, 0, 0, 0]"
    }

    @Test
    public void listToArrayTest() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        Integer[] array = list.toArray(new Integer[0]);
        System.out.println(Arrays.toString(array));
    }

    @Test
    public void arrayStreamBoxedTest() {
        int[] arr = {1, 2, 3, 4, 5, 6};
        // 得到int[]数组中所有偶数的和
        int sumOfEvens = Arrays.stream(arr).boxed().filter(i -> i % 2 == 0).mapToInt(i -> i).sum();
        System.out.println(sumOfEvens);  // prints "12"
    }


}
