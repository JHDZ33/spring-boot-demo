package com.jhdz.orm.design.service.strategy;

import com.jhdz.orm.design.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserContent {
    @Autowired
    private UserStrategyFactory userStrategyFactory;

    public void introduce(User user) {
        UserStrategy userBean = userStrategyFactory.getUserBean(user.getWorkType());
        userBean.myName(user);
    }

    public void myAbility(User user) {
        UserStrategy userBean = userStrategyFactory.getUserBean(user.getWorkType());
        userBean.myAbility(user);
    }
}
