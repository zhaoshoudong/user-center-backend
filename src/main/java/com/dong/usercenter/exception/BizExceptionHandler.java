package com.dong.usercenter.exception;

import com.dong.usercenter.common.BaseResponse;
import com.dong.usercenter.common.ErrorCodeEnum;
import com.dong.usercenter.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @BelongsPackage: com.dong.usercenter.exception
 * @Author: shouDong.zhao
 * @CreateTime: 2024/9/4
 * @Description: 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class BizExceptionHandler {
    /**
     * 捕获 自定义的BusinessException异常
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        return ResultUtils.error(e.getCode(), e.getMessage(), e.getDescription());
    }

    /**
     * 捕获 RuntimeException 异常
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException:" + e.getMessage(), e);
        return ResultUtils.error(ErrorCodeEnum.SYSTEM_ERROR.getCode(), e.getMessage(), "");
    }
}
