package com.dong.usercenter.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @BelongsPackage: com.dong.usercenter.util
 * @Author: shouDong.zhao
 * @CreateTime: 2024/8/29
 */
public class MD5Util {

    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xFF & b);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
