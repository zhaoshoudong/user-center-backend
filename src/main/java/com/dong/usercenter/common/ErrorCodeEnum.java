package com.dong.usercenter.common;

import lombok.Getter;

/**
 * @BelongsPackage: com.dong.usercenter.common
 * @Author: shouDong.zhao
 * @CreateTime: 2024-09-04 22:03
 * @Description: 错误码枚举
 */
@Getter
public enum ErrorCodeEnum {

    SYSTEM_ERROR(500, "系统内部异常", ""),
    USER_NOT_EXITS(10001, "该用户未注册", ""),
    USER_IS_EXITS(10002, "该用户已注册", ""),
    USERNAME_PASSWORD_NOT_CORRECT(10003, "用户名或密码不正确!", ""),
    USER_LOGIN_EXPIRED(10004, "登录已过期,请重新登陆!", ""),
    USER_NOT_ADMIN(10005, "只有管理员才可以删除用户!", ""),
    CHECK_PASSWORD_NOT_CORRECT(10006, "两次输入的密码不一致", ""),
    USERNAME_PASSWORD_IS_NULL(10007, "用户名或密码不能为空", "");


    private final int code;
    private final String message;

    private final String description;

    ErrorCodeEnum(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }
}
