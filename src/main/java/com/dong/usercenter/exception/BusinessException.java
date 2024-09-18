package com.dong.usercenter.exception;

import lombok.Getter;

/**
 * @BelongsPackage: com.dong.usercenter.exception
 * @Author: shouDong.zhao
 * @CreateTime: 2024/9/4
 */
@Getter
public class BusinessException extends RuntimeException {

    private final int code;
    private final String description;

    public BusinessException(int code, String message, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }
}
