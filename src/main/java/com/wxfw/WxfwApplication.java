package com.wxfw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
@MapperScan({"com.wxfw.dao"})
public class WxfwApplication {
    public static void main(String[] args) {
        SpringApplication.run(WxfwApplication.class, args);
    }

}
