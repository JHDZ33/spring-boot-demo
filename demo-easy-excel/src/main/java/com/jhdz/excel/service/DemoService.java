package com.jhdz.excel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jhdz.excel.entity.DemoData;

import javax.servlet.http.HttpServletResponse;

public interface DemoService extends IService<DemoData> {

    void export(DemoData demoData, HttpServletResponse response);

}
