package com.jhdz.excel.test;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.jhdz.excel.entity.DemoData;
import com.jhdz.excel.util.TestFileUtil;
import org.junit.Test;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 测试类的easyExcel依赖版本：3.2.1
 * 可能一些用法不一样，但功能基本是一样的，具体参考官方文档：https://easyexcel.opensource.alibaba.com/docs/current/quickstart/write
 */
public class ExcelWriteTest {

    @Test
    public void simpleWrite() {

        // 实际业务中一般是这里直接返回给前端文件，此处httpServletResponse就是返回给前端的响应。
        HttpServletResponse httpServletResponse = null;
        ServletOutputStream outputStream = null;
        try {
            outputStream = httpServletResponse.getOutputStream();
            EasyExcel.write(outputStream, DemoData.class)
                .sheet("测试报表")
                .doWrite(() -> {
                    // 分页查询数据
                    return data();
                });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 只导出指定列
     */

    @Test
    public void excludeOrIncludeWrite() {

        try {
            // 只导出name和address这两列
            Set<String> includeColumnFiledNames = new HashSet<String>();
            includeColumnFiledNames.add("name");
            includeColumnFiledNames.add("address");
            EasyExcel.write("excludeOrIncludeWrite.xlsx", DemoData.class)
                .includeColumnFieldNames(includeColumnFiledNames)
                .sheet("测试报表")
                .doWrite(() -> {
                    // 分页查询数据
                    return data();
                });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 多个sheet写入
     */
    @Test
    public void sheetWrite() {
        try {
            List<DemoData> dataList = data();
            // 只导出name和address这两列
            Set<String> includeColumnFiledNames = new HashSet<String>();
            includeColumnFiledNames.add("name");
            includeColumnFiledNames.add("address");
            try (ExcelWriter excelWriter = EasyExcel.write("sheetWrite.xlsx", DemoData.class)
                .includeColumnFieldNames(includeColumnFiledNames)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()) // 自动对齐
                .build()) {
                // 随便模拟个场景，循环两次生成两个sheet，一个放0和偶数，另一个放奇数
                for (int i = 0; i < 2; i++) {
                    WriteSheet writeSheet = EasyExcel.writerSheet(i, "sheet" + i).build();
                    if (i == 0) {
                        excelWriter.write(dataList.stream().filter(demoData -> demoData.getId() == 0 || demoData.getId() % 2 == 0).collect(Collectors.toList()), writeSheet);
                    } else {
                        excelWriter.write(dataList.stream().filter(demoData -> demoData.getId() != 0 && demoData.getId() % 2 == 1).collect(Collectors.toList()), writeSheet);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // 模拟数据库分页查询
    private List<DemoData> data() {
        List<DemoData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setId(i);
            data.setUid(i + "");
            data.setName("名字" + i);
            data.setAddress("地址" + i);
            data.setDate(new Date());
            data.setDoubleNum(0.56);
            list.add(data);
        }
        return list;
    }
}
