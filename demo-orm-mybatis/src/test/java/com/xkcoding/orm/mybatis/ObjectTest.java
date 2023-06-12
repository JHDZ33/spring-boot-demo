package com.xkcoding.orm.mybatis;

import org.junit.Test;

public class ObjectTest {

    @Test
    public void subTest() {
        String s = "/oss/20220930/f695s2851a5115f6156.jpeg";
        String substring = s.substring(s.lastIndexOf("/") + 1);
        System.out.println(
            substring
        );
    }

    @Test
    public void stringTest(){
        String a = new String("aa");
        String b = new String("aa");
        String aa = "aa";
        String bb = "aa";
        String aa1 = aa.concat("a");
        System.out.println(aa1);
        String cc = "aaa";
        System.out.println(a == b ); // false
        System.out.println(a.equals(b)); // true
        System.out.println(aa == bb); // true
        System.out.println(aa.equals(bb)); // true
        System.out.println(aa.equals(a)); // true
        System.out.println(aa1 == cc); // false
    }

    @Test
    public void stringInterTest(){
        // 在堆中创建字符串对象”Java“
        // 将字符串对象”Java“的引用保存在字符串常量池中
        String s1 = "Java";
        // 直接返回字符串常量池中字符串对象”Java“对应的引用
        String s2 = s1.intern();
        // 会在堆中在单独创建一个字符串对象
        String s3 = new String("Java");
        // 直接返回字符串常量池中字符串对象”Java“对应的引用
        String s4 = s3.intern();
        // s1 和 s2 指向的是堆中的同一个对象
        System.out.println(s1 == s2); // true
        // s3 和 s4 指向的是堆中不同的对象
        System.out.println(s3 == s4); // false
        // s1 和 s4 指向的是堆中的同一个对象
        System.out.println(s1 == s4); //true
    }
}
