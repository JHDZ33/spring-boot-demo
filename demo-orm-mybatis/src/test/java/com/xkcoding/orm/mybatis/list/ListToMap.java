package com.xkcoding.orm.mybatis.list;

import com.xkcoding.orm.mybatis.entity.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ListToMap {
    @Test
    public void listToMapTest(){
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setId((long) i);
            user.setName("name" + i);
            list.add(user);
        }
        //  (key1, key2) -> key2)  表示如果生成的map key重复的情况下，取后者
        Map<Long, String> collect1 = list.stream().collect(Collectors.toMap(User::getId, User::getName, (key1, key2) -> key2));
        // Function.identity()表示list中的元素
        Map<Long, User> collect2 = list.stream().collect(Collectors.toMap(User::getId, Function.identity(), (key1, key2) -> key2));
        System.out.println(collect1.toString());
        System.out.println(collect2.toString());

    }
}
