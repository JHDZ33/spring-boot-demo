package com.jhdz.orm.design.service.strategy;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserStrategyFactory {
    /**
     * 存储职业类型和策略bean的关系
     */
    public static Map<Integer, UserStrategy> WORK_TYPE = new HashMap<>();

    /**
     * 注册策略bean
     */
    public static void registerBean(Integer workType, UserStrategy userStrategy){
        WORK_TYPE.put(workType, userStrategy);
    }

    /**
     * 获取策略bean
     */
    public UserStrategy getUserBean(Integer workType) {
        return WORK_TYPE.get(workType);
    }

}
