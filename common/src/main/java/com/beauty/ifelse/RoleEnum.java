package com.beauty.ifelse;

/**
 * Description: TODO <BR>
 *
 * @Author: zhao.song
 * @Date: 2020/5/27 9:48
 * @Version: 1.0
 */
public enum RoleEnum implements RoleOperation {


    ROLE_ROOT_ADMIN {
        /**
         * Description: 表示某个角色可以做的操作 <BR>
         *
         * @return java.lang.String
         * @author zhao.song    2020/5/27 9:48
         */
        @Override
        public String op() {
            return "i'm root admin!";
        }
    },

    ROLE_OPER_ADMIN {
        @Override
        public String op() {
            return "i'm oper admin!";
        }
    },
    ROLE_NORMAL {
        @Override
        public String op() {
            return "i'm normal user!";
        }
    };
}
