package com.asim.curd_demo.repositories.impl;

import com.asim.curd_demo.model.userbalance.CreateUserBalanceTransactionResponse;
import com.asim.curd_demo.model.userbalance.UserBalanceTransactionDTO;
import com.asim.curd_demo.model.userbalance.UserBalanceTransactionResponse;
import com.asim.curd_demo.repositories.UserBalanceTransactionRepository;
import com.asim.curd_demo.utils.JsonUtils;
import com.asim.curd_demo.utils.QueryFile;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserBalanceTransactionRepositoryImpl extends BaseRepository implements UserBalanceTransactionRepository {
    @Override
    public UserBalanceTransactionDTO getUserBalanceTransactionById(long transactionId) {

        Map<String, String> variables = new HashMap<>();

        variables.put("$id" , String.valueOf(transactionId));
        String queryResult = this.execute(QueryFile.USER_BALANCE_TRANSACTION_FIND_BY_ID, variables );
        UserBalanceTransactionResponse response =  JsonUtils.toJson(queryResult, UserBalanceTransactionResponse.class);

        if (response == null || response.getData() == null || CollectionUtils.isEmpty(response.getData().getItems()))
            return null;

        return response.getData().getItems().get(0);
    }

    @Override
    public CreateUserBalanceTransactionResponse createUserBalanceTransaction(UserBalanceTransactionDTO entity) {
        Map<String, String> variables = new HashMap<>();

        variables.put("$user_id" , String.valueOf(entity.getUserId()));
        variables.put("$status" , String.valueOf(entity.getStatus()));
        variables.put("$amount" , String.valueOf(entity.getAmount()));
        variables.put("$action_type" , String.valueOf(entity.getActionType()));

        String queryResult = this.execute(QueryFile.USER_BALANCE_TRANSACTION_CREATE, variables );
        return JsonUtils.toJson(queryResult, CreateUserBalanceTransactionResponse.class);
    }

    @Override
    public UserBalanceTransactionResponse updateUserBalanceTransactionStatus(UserBalanceTransactionDTO entity) {
        Map<String, String> variables = new HashMap<>();

        variables.put("$id" , String.valueOf(entity.getId()));
        variables.put("$status" , String.valueOf(entity.getStatus()));

        String queryResult = this.execute(QueryFile.USER_BALANCE_TRANSACTION_UPDATE_STATUS, variables );
        return JsonUtils.toJson(queryResult, UserBalanceTransactionResponse.class);
    }
}
