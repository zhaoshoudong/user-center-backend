package com.dong.usercenter.request.user;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.dong.usercenter.enums.UserGenderEnum;
import com.dong.usercenter.enums.UserStatusEnum;
import lombok.Data;

/**
 * @BelongsPackage: com.dong.usercenter.request.user
 * @Author: shouDong.zhao
 * @CreateTime: 2024/9/16
 * @Description: TODO
 */

@Data
public class UserUpdateRequest {
    private String id;
    private String username;
    private String userAccount;
    private String avatarUrl;
    private String phone;
    private String email;
    private UserGenderEnum gender;
    private UserStatusEnum userStatus;
    private Integer userRole;
}
