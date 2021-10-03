package com.zzlin.config;

import com.zzlin.controller.interceptor.UserTokenInterceptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zlin
 * @date 20210118
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 实现静态资源的映射
     * @param registry registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                // 映射swagger2
                .addResourceLocations("classpath:/META-INF/resources/")
                // 映射本地静态资源
                .addResourceLocations("file:/workspaces/images/");
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public UserTokenInterceptor userTokenInterceptor() {
        return new UserTokenInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userTokenInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/index/**")
                .excludePathPatterns("/items/**")
                .excludePathPatterns("/passport/**")
                .excludePathPatterns("/myorders/deliver")
                .excludePathPatterns("/orders/token")
                .excludePathPatterns("/orders/notifyMerchantOrderPaid");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
