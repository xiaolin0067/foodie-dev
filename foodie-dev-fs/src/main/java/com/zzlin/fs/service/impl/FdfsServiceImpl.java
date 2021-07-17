package com.zzlin.fs.service.impl;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
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
    private FastFileStorageClient fastFileStorageClient;

    @Override
    public String upload(MultipartFile file, String fileExtName) throws Exception {
        StorePath storePath = fastFileStorageClient.uploadFile(
                file.getInputStream(), file.getSize(), fileExtName, null);
        return storePath.getFullPath();
    }
}
