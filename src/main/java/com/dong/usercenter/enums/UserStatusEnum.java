package com.dong.usercenter.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @BelongsPackage: com.dong.usercenter.enums
 * @Author: shouDong.zhao
 * @CreateTime: 2024-09-16 10:36
 * @Description: 用户状态枚举
 */
@Getter
@AllArgsConstructor
public enum UserStatusEnum {
    ONLINE(0,"启用"),
    OUTLINE(1,"禁用");

    @EnumValue
    private final int user_status;
    @JsonValue
    private final String description;
}
