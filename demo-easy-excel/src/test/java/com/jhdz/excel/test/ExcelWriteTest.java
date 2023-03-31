package com.jhdz.excel.test;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;
import com.jhdz.excel.entity.DemoData;
import com.jhdz.excel.util.TestFileUtil;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class ExcelWriteTest {

    @Test
    public void simpleWrite() {
        String fileName = TestFileUtil.getPath() + "simpleWrite" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, DemoData.class)
            .sheet("模板")
            .doWrite(() -> {
                // 分页查询数据
                return data();
            });
    }




    // 模拟数据库分页查询
    private List<DemoData> data() {
        List<DemoData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
}
