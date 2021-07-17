package com.zzlin.fs.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.zzlin.fs.config.FileConfig;
import com.zzlin.fs.service.FdfsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zlin
 * @date 20210717
 */
@Service
public class FdfsServiceImpl implements FdfsService {

    @Autowired
    private FileConfig fileConfig;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Override
    public String upload(MultipartFile file, String fileExtName) throws Exception {
        StorePath storePath = fastFileStorageClient.uploadFile(
                file.getInputStream(), file.getSize(), fileExtName, null);
        return storePath.getFullPath();
    }

    @Override
    public String uploadAliyunOSS(MultipartFile file, String userId, String fileExtName) throws Exception {
        OSS ossClient = new OSSClient(
                fileConfig.getAliyunOssEndpoint(),
                fileConfig.getAliyunOssAccessKeyId(),
                fileConfig.getAliyunOssAccessKeySecret());
        String uploadPath = fileConfig.getAliyunOssObjectName() + "/" + userId + "/" + userId + "." + fileExtName;
        ossClient.putObject(fileConfig.getAliyunOssBucketName(), uploadPath, file.getInputStream());
        ossClient.shutdown();
        return fileConfig.getAliyunOssServerUrl() + uploadPath;
    }
}
