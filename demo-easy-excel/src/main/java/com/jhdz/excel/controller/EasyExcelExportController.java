package com.jhdz.excel.controller;

import com.jhdz.excel.entity.DemoData;
import com.jhdz.excel.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

public class EasyExcelExportController {

    @Autowired
    private DemoService demoService;

    @RequestMapping("/export")
    public void list(@RequestBody DemoData demoData, HttpServletResponse response) {
        demoService.export(demoData, response);
    }
}
