package com.zzlin.fs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author zlin
 * @date 20210717
 */
@Component
@PropertySource("classpath:fdfs.properties")
@ConfigurationProperties(prefix = "file")
public class FileConfig {

    private String serverUrl;

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
}
