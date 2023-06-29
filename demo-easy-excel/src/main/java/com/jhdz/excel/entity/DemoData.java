package com.jhdz.excel.entity;



import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
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
    private Integer id;

    private String uid;

    // value就是导出表格的列名，可以做一些复杂表头，比如以下两个字段有个共同的列，该列下有分叉为两个列
    // index就是顺序
    @ExcelProperty(value = {"个人信息", "姓名"}, index = 0)
    private String name;

    @ExcelProperty(value = {"个人信息", "地址"}, index = 1)
    private String address;

    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    @ExcelProperty("日期标题")
    private Date date;

    @NumberFormat("#.##%")
    @ExcelProperty(value = "数字标题")
    private Double doubleNum;


}
