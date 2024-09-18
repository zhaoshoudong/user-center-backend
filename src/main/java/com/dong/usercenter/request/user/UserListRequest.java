package com.dong.usercenter.request.user;

import lombok.Data;

/**
 * @BelongsPackage: com.dong.usercenter.request
 * @Author: shouDong.zhao
 * @CreateTime: 2024/8/29
 */
@Data
public class UserListRequest {
    private String username;
    private String phone;
    private String email;
}
