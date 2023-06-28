package com.jhdz.excel.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jhdz.excel.dao.DemoDao;
import com.jhdz.excel.entity.DemoData;
import com.jhdz.excel.service.DemoService;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Service
public class DemoServiceImpl extends ServiceImpl<DemoDao, DemoData> implements DemoService {


    @Override
    public void export(DemoData demoData, HttpServletResponse response) {
        try {
            // 关于表格进一步改进查看官网文档
            ServletOutputStream outputStream = response.getOutputStream();
            EasyExcel.write(outputStream, DemoData.class)
                .sheet("data报表")
                .doWrite(() -> {
                    // 分页查询数据
                    return data();
                });
        } catch (Exception e) {
            e.printStackTrace();
        }

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
