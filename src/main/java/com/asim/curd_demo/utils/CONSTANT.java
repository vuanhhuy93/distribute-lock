package com.asim.curd_demo.utils;

public interface CONSTANT {

    class ORDER_STATUS{
        public static final int CREATE = 1;
        public static final int SUCCESS = 2;

        public static final int FAIL = 3;

        public static final int CANCEL = 4;
    }

    class USER_BALANCE_TRANSACTION_STATUS{
        public static final int CREATE = 1;

        public static final int SUCCESS = 2;

        public static final int FAIL = 3;

        public static final int PROCESS = 4;

        public static final int ROLLBACK = 5;
    }
}
