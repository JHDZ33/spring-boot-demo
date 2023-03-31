package com.jhdz.excel;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class EasyExcelApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(EasyExcelApplication.class);
        app.run(args);
    }
}
