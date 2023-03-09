package com.xkcoding.orm.mybatis.reflect;

public class FactoryTest {
    interface fruit{
        public abstract void eat();
    }

    static class Apple implements fruit{
        @Override
        public void eat() {
            System.out.println("Apple");
        }
    }

    static class Orange implements fruit{

        @Override
        public void eat() {
            System.out.println("Orange");
        }
    }
    //构造工厂类
    //也就是说以后如果我们在添加其他的实例的时候只需要修改工厂类就行了
    static class FruitFactory{
        public static fruit getInstance(String fruitName){
            fruit f=null;
            if("Apple".equals(fruitName)){
                f=new Apple();
            }
            if("Orange".equals(fruitName)){
                f=new Orange();
            }
            return f;
        }
    }

    static class ReflectFruitFactory{
        public static fruit getInstance(String className){
            fruit f=null;
            try {
                f = (fruit)Class.forName(className).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return f;
        }
    }

    // 这样使用反射的方式，无论我们创建多少fruit的实例，都不需要修改反射生成的工厂模板，有效的降低了系统耦合性！
    static class hello{
        public static void main(String[] a){
            fruit instance = ReflectFruitFactory.getInstance("com.xkcoding.orm.mybatis.reflect.FactoryTest$Orange");
            if (instance != null) {
                instance.eat();
            }
        }
    }
}
