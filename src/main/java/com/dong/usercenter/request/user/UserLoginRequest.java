package com.dong.usercenter.request.user;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @BelongsPackage: com.dong.usercenter.request
 * @Author: shouDong.zhao
 * @CreateTime: 2024/8/29
 */
@Data
public class UserLoginRequest {
    @NotBlank(message = "账号不能为空")
    private String userAccount;

    @NotBlank(message = "密码不能为空")
    private String userPassword;
}
