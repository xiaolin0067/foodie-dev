package com.zzlin.fs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author zlin
 * @date 20210717
 */
@SpringBootApplication
@MapperScan(basePackages = "com.zzlin.mapper")
@ComponentScan(basePackages = {"com.zzlin", "org.n3r.idworker"})
public class FSApp {

    public static void main(String[] args) {
        // 启动
        SpringApplication.run(FSApp.class, args);
    }
}
