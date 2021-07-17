package com.zzlin.fs.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author zlin
 * @date 20210717
 */
public interface FdfsService {

    String upload(MultipartFile file, String fileExtName) throws Exception;

    String uploadAliyunOSS(MultipartFile file, String userId, String fileExtName) throws Exception;

}
