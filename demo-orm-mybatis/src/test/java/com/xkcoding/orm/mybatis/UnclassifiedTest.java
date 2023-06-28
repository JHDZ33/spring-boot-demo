package com.xkcoding.orm.mybatis;

import com.xkcoding.orm.mybatis.entity.DataEntity;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

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

    @Test
    public void someTest() {
        Map<String, List<Map<String, Integer>>> hashMap = new HashMap<>();
        /**
         * {
         *     "roadSectionId1": {
         *         "时间段1"：count2,
         *         "时间段2"：count2,
         *         ...
         *     },
         *     "roadSectionId2": {
         *          *         "时间段1"：count2,
         *          *         "时间段2"：count2,
         *          *         ...
         *          *     }
         * }
         */
    }


    /**
     *
     * @param count
     * @return
     */
    private List<DataEntity> getDataList(int count) {
        List<DataEntity> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            DataEntity dataEntity = new DataEntity();
            dataEntity.setId(i);
            dataEntity.setName("name" + i);
            dataEntity.setNow(new Date());
            list.add(dataEntity);
        }
        return list;
    }

    @Test
    public void getIndex() {
        // 生成个长度为10的对象List
        List<DataEntity> dataList = getDataList(10);
        for (DataEntity d:dataList) {
            System.out.println(dataList.indexOf(d));
        }
    }
}
