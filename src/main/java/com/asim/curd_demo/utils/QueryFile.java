package com.asim.curd_demo.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class QueryFile {
    public static final String PRODUCT_FIND_BY_ID = "query/product/findById";

    public static final String WAREHOUSE_FIND_BY_ID = "query/warehouse/findById";
    public static final String WAREHOUSE_UPDATE_REVERSE_ACTIVE = "query/warehouse/update_reverse_active";
    public static final String WAREHOUSE_UPDATE = "query/warehouse/update";
    public static final String USER_BALANCE_FIND_BY_ID = "query/userbalance/findById";
    public static final String USER_BALANCE_UPDATE_REVERSE_BALANCE = "query/userbalance/update_reverse_balance";
    public static final String USER_BALANCE_UPDATE_USER_BALANCE = "query/userbalance/update_user_balance";

    public static final String USER_BALANCE_UPDATE_USER_BALANCE_AND_TRANSACTION = "query/userbalance/update_balance_and_transaction";

    public static final String USER_BALANCE_TRANSACTION_FIND_BY_ID = "query/userbalancetransaction/findById";

    public static final String USER_BALANCE_TRANSACTION_CREATE = "query/userbalancetransaction/create_user_balance_transaction";

    public static final String USER_BALANCE_TRANSACTION_UPDATE_STATUS = "query/userbalancetransaction/update_status_user_balance_transaction";

    public static final String ORDER_FINd_BY_ID = "query/order/findById";

    public static final String ORDER_CREATE = "query/order/create_order";

    public static final String ORDER_UPDATE_STATUS = "query/order/update_status_order";
    public static String readFile(ClassLoader classLoader, String filePath) {

        try (InputStream inputStream = classLoader.getResourceAsStream(filePath);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {
            String line;
            StringBuilder queryBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                queryBuilder.append(line);
            }
            return queryBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static String readFile( String filePath) {

        ClassLoader classLoader = QueryFile.class.getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(filePath);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {
            String line;
            StringBuilder queryBuilder = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                queryBuilder.append(line);
            }
            return queryBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String parseRequest(ClassLoader classLoader, String filePath) {
        return QueryFile.readFile(classLoader, filePath);
    }

    public static String parseRequest(String filePath) {
        return QueryFile.readFile(filePath);
    }
}
