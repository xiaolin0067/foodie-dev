package com.zzlin.performance.async;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author zlin
 * @date 20220325
 */
@Configuration
public class Beans {

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }

    @Bean
    public AsyncRestTemplate asyncRestTemplate() {
        return new AsyncRestTemplate();
    }

}
