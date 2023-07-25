package com.xkcoding.orm.mybatis.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DataEntity implements DataEntityParent,Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private Date now;

}
