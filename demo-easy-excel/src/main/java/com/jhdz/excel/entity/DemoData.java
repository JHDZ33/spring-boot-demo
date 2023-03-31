package com.jhdz.excel.entity;



import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@TableName("demo")
@Getter
@Setter
@EqualsAndHashCode
public class DemoData {
    @TableId
    private String id;
    private String string;
    private Date date;
    private Double doubleData;
}
