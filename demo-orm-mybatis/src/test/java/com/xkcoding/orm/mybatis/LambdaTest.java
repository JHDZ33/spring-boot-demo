package com.xkcoding.orm.mybatis;


import org.junit.Test;


public class LambdaTest {

    @Test
    public void test1() {

        TestLambda testLambda = new TestLambda() {

            @Override
            public String get1() {
                return "1";
            }

            @Override
            public String get2() {
                return "2";
            }
        };
        System.out.println(testLambda.get1());
    }

}


interface TestLambda{
    String get1();
    String get2();
}
