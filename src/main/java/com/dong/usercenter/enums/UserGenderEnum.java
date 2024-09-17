package com.dong.usercenter.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @BelongsPackage: com.dong.usercenter.enums
 * @Author: shouDong.zhao
 * @CreateTime: 2024-09-14 22:27
 * @Description: 用户性别枚举
 */
@Getter
@AllArgsConstructor
public enum UserGenderEnum {
    MALE(0, "男"),
    FEMALE(1, "女");

    @EnumValue
    private final int gender;
    @JsonValue
    private final String description;
}
