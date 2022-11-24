package com.beauty.ifelse;

/**
 * Description: TODO <BR>
 *
 * @Author: zhao.song
 * @Date: 2020/5/27 9:55
 * @Version: 1.0
 */
public class JudgeRole {

    public static String judge(String roleName) {
        RoleEnum roleEnum = RoleEnum.valueOf(roleName);
        return roleEnum.op();
    }
}
