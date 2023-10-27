package com.xkcoding.orm.mybatis.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DataEntity2 implements DataEntityParent,Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private String name2;
    private Integer age;
    private Date now;

}
