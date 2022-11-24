package com.beauty.ifelse;

import com.beauty.DocPlatformHelper;

/**
 * Description: 测试枚举替代ifelse <BR>
 * <p>
 * 剩余的工厂模式和策略模型请见:
 * <p>{@link:https://www.toutiao.com/i6810757596232811016/}</p>
 *
 * @Author: zhao.song
 * @Date: 2020/5/27 9:56
 * @Version: 1.0
 */
public class RoleOperationDemoTest {

    public static void main(String[] args) {
        System.out.println(DocPlatformHelper.DocPlatformTypeEnum.valueOf("DAIYONG").getPlatformName());
        System.out.println(JudgeRole.judge("ROLE_ROOT_ADMIN"));
        System.out.println(RoleEnum.valueOf("ROLE_ROOT_ADMIN").name());

//        System.out.println(JudgeRole.judge("sss"));
    }
}
