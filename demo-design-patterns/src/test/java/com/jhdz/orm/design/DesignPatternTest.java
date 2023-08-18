package com.jhdz.orm.design;

import com.jhdz.orm.design.entity.User;
import com.jhdz.orm.design.service.strategy.UserContent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DesignPatternTest {

    @Autowired
    UserContent userContent;

    /**
     * 策略模式的主要好处就是：
     * 1.在程序的执行中动态的选择对象应该走的程序，像是本例就是根据对象的workType属性去选择
     * 2.无需对上下文进行更改就能引入新的策略，比如现在就只有doctorStrategy和teacherStrategy，之后再写个lawyerStrategy，就不用改上下文代码
     */
    @Test
    public void test1() {
        User user = new User();
        user.setWorkType(1);
        user.setName("张三");
        userContent.introduce(user);
        userContent.myAbility(user);

        user.setWorkType(2);
        user.setName("李四");
        userContent.introduce(user);
        userContent.myAbility(user);
    }
}
