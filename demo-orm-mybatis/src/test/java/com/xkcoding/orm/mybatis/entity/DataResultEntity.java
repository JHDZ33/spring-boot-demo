package com.xkcoding.orm.mybatis.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class DataResultEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private Date now;

    private DataEntityParent dataEntity;
}
