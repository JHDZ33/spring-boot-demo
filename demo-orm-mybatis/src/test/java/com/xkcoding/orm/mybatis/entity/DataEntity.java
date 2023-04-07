package com.xkcoding.orm.mybatis.entity;

import lombok.Data;

import java.util.Date;

@Data
public class DataEntity {
    private long id;
    private String name;
    private Date now;

}
