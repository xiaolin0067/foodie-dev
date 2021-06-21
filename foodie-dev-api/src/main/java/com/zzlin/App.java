package com.zzlin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author zlin
 * @date 20201105
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
// 扫描mapper
@MapperScan(basePackages = "com.zzlin.mapper")
// 默认扫描com.zzlin包下的bean，需要添加扫描组件包org.n3r.idworker下的bean
@ComponentScan(basePackages = {"com.zzlin", "org.n3r.idworker"})
@EnableScheduling
// 开启使用redis作为springsession
@EnableRedisHttpSession
public class App {

    public static void main(String[] args) {
        // 启动
        SpringApplication.run(App.class, args);
    }

}
