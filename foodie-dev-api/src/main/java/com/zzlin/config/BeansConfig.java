package com.zzlin.config;

import com.zzlin.performance.objectpool.datasource.ZlinDataSource;
import com.zzlin.performance.objectpool.datasource.actuator.DataSourceEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author zlin
 * @date 20220320
 */
//@Configuration
//public class BeansConfig {
//
//    @Bean
//    @Primary
//    public ZlinDataSource dataSource() {
//        return new ZlinDataSource();
//    }
//
//    @Bean
//    public DataSourceEndpoint dataSourceEndpoint() {
//        return new DataSourceEndpoint(dataSource());
//    }
//
//}
