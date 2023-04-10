package com.xkcoding.orm.mybatis;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class bubbleSortTest {

    @Test
    public void test1() {
        int[] array = new int[] {5, 2, 1, 19, 11};
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j+1]) {
                    int temp = array[j+1];
                    array[j+1] = array[j];
                    array[j] = temp;
                }
            }
        }
        // 数组转List
        List<Integer> collect = Arrays.stream(array).boxed().collect(Collectors.toList());
        System.out.println(collect.toString());
    }
}
