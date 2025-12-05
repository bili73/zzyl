package com.zzyl.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 密码工具类
 */
public class PasswordUtil {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * 加密密码
     * @param password 明文密码
     * @return 加密后的密码
     */
    public static String encodePassword(String password) {
        return encoder.encode(password);
    }

    /**
     * 验证密码
     * @param password 明文密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    public static boolean matches(String password, String encodedPassword) {
        return encoder.matches(password, encodedPassword);
    }
}