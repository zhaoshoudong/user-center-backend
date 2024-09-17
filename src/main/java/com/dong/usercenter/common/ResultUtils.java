package com.dong.usercenter.common;

import cn.hutool.http.HttpStatus;

/**
 * @BelongsPackage: com.dong.usercenter.common
 * @Author: shouDong.zhao
 * @CreateTime: 2024/9/1
 * @Description: 统一返回结果
 */
public class ResultUtils<T> {
    public static <T> BaseResponse<T> success() {

        return new BaseResponse<>(HttpStatus.HTTP_OK, "success", null, null);
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(HttpStatus.HTTP_OK, "success", null, data);
    }

    public static <T> BaseResponse<T> success(T data, String description) {
        return new BaseResponse<>(HttpStatus.HTTP_OK, "success", description, data);
    }

    public static <T> BaseResponse<T> success(T data, String message, String description) {
        return new BaseResponse<>(HttpStatus.HTTP_OK, message, description, data);
    }

    public static <T> BaseResponse<T> error(int code) {
        return new BaseResponse<>(code, "error", null, null);
    }

    public static <T> BaseResponse<T> error(String description) {
        return new BaseResponse<>(HttpStatus.HTTP_OK, "error", description, null);
    }

    public static <T> BaseResponse<T> error(int code, String description) {
        return new BaseResponse<>(code, "error", description, null);
    }

    public static <T> BaseResponse<T> error(int code, String message, String description) {
        return new BaseResponse<>(code, message, description, null);
    }

}
