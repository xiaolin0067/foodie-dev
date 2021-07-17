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

    private String aliyunOssEndpoint;

    private String aliyunOssAccessKeyId;

    private String aliyunOssAccessKeySecret;

    private String aliyunOssBucketName;

    private String aliyunOssObjectName;

    private String aliyunOssServerUrl;

    public String getAliyunOssServerUrl() {
        return aliyunOssServerUrl;
    }

    public void setAliyunOssServerUrl(String aliyunOssServerUrl) {
        this.aliyunOssServerUrl = aliyunOssServerUrl;
    }

    public String getAliyunOssEndpoint() {
        return aliyunOssEndpoint;
    }

    public void setAliyunOssEndpoint(String aliyunOssEndpoint) {
        this.aliyunOssEndpoint = aliyunOssEndpoint;
    }

    public String getAliyunOssAccessKeyId() {
        return aliyunOssAccessKeyId;
    }

    public void setAliyunOssAccessKeyId(String aliyunOssAccessKeyId) {
        this.aliyunOssAccessKeyId = aliyunOssAccessKeyId;
    }

    public String getAliyunOssAccessKeySecret() {
        return aliyunOssAccessKeySecret;
    }

    public void setAliyunOssAccessKeySecret(String aliyunOssAccessKeySecret) {
        this.aliyunOssAccessKeySecret = aliyunOssAccessKeySecret;
    }

    public String getAliyunOssBucketName() {
        return aliyunOssBucketName;
    }

    public void setAliyunOssBucketName(String aliyunOssBucketName) {
        this.aliyunOssBucketName = aliyunOssBucketName;
    }

    public String getAliyunOssObjectName() {
        return aliyunOssObjectName;
    }

    public void setAliyunOssObjectName(String aliyunOssObjectName) {
        this.aliyunOssObjectName = aliyunOssObjectName;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }
}
