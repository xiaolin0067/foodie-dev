package com.zzlin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author zlin
 * @date 20210628
 */
@SpringBootApplication
@MapperScan(basePackages = "com.zzlin.mapper")
@ComponentScan(basePackages = {"com.zzlin", "org.n3r.idworker"})
public class SSOApp {

    public static void main(String[] args) {
        SpringApplication.run(SSOApp.class, args);
    }

}
