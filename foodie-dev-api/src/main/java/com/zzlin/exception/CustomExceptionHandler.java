package com.zzlin.exception;

import com.zzlin.utils.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @author zlin
 * @date 20210124
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler
    public Result handlerUploadFileMaxSize(MaxUploadSizeExceededException e) {
        return Result.errorMsg("上传文件大小超过限制");
    }
}
