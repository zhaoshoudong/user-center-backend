package com.dong.usercenter.common;

import lombok.Data;

/**
 * @BelongsPackage: com.dong.usercenter.common
 * @Author: shouDong.zhao
 * @CreateTime: 2024/9/1
 * @Description: 通用返回类
 */
@Data
public class BaseResponse<T> {
    private int code;
    private String message;
    private String description;
    private T data;

    public BaseResponse(int code, String message, String description, T data) {
        this.code = code;
        this.message = message;
        this.description = description;
        this.data = data;
    }

    public BaseResponse(int code, String message, String description) {
        this(code, message, description, null);
    }
}
