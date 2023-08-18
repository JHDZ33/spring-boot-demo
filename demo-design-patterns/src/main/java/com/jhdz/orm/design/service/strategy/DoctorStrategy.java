package com.jhdz.orm.design.service.strategy;

import com.jhdz.orm.design.entity.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
public class DoctorStrategy implements UserStrategy, InitializingBean {
    final static Integer workType = 1;

    /**
     * 在bean初始话时注册
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        UserStrategyFactory.registerBean(workType,this);
    }

    @Override
    public void myName(User user) {
        System.out.println("我是一名**医生**，姓名是" + user.getName());
    }

    @Override
    public void myAbility(User user) {
        System.out.println("工作是拯救生命");
    }
}
