package com.xkcoding.orm.mybatis;

import org.junit.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class UnclassifiedTest {

    @Test
    public void decimalFormatTest() {
        BigDecimal num = new BigDecimal(100.114514);
        // 四舍五入法取小数点后三位
        DecimalFormat df1 = new DecimalFormat("#.###");
        String s = df1.format(num);
        System.out.println(s);
        /**
         * 你还可以指定其他格式,如:
         * - ###.## - 整数部分至少 3 位,小数 2 位
         * - 0.00 - 小数点前补 0,2 位小数
         * - #,###.## - 每三位添加分隔符(逗号)
         */
        DecimalFormat df2 = new DecimalFormat("0000.000");
        System.out.println(df2.format(num));
    }
}
