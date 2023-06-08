package com.asim.curd_demo.repositories.impl;

import com.asim.curd_demo.config.graphql.GraphQLUtils;
import com.asim.curd_demo.model.product.ProductResponse;
import com.asim.curd_demo.model.userbalance.UpdateBalanceAndTransactionResponse;
import com.asim.curd_demo.model.userbalance.UpdateUserBalanceResponse;
import com.asim.curd_demo.model.userbalance.UserBalanceDTO;
import com.asim.curd_demo.model.userbalance.UserBalanceResponse;
import com.asim.curd_demo.repositories.UserBalanceRepository;
import com.asim.curd_demo.utils.ApplicationException;
import com.asim.curd_demo.utils.JsonUtils;
import com.asim.curd_demo.utils.QueryFile;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;


@Repository
@Log4j2
public class UserBalanceRepositoryImpl extends BaseRepository implements UserBalanceRepository {
    @Override
    public UserBalanceDTO findUserBalanceByUserId(long userId) throws ApplicationException {

        Map<String, String> variables = new HashMap<>();

        variables.put("$user_id", String.valueOf(userId));
        String queryResult = this.execute(QueryFile.USER_BALANCE_FIND_BY_ID, variables);
        UserBalanceResponse userBalanceResponse = JsonUtils.toJson(queryResult, UserBalanceResponse.class);

        if (userBalanceResponse == null || userBalanceResponse.getData() == null || CollectionUtils.isEmpty(userBalanceResponse.getData().getItems()))
            throw new ApplicationException(-10, "not load user balance");

        return userBalanceResponse.getData().getItems().get(0);

    }

    @Override
    public UserBalanceDTO update(long userId, double userBalance, double reverseBalance, double version) {
        Map<String, String> variables = new HashMap<>();

        variables.put("$user_id", String.valueOf(userId));
        variables.put("$reverse_balance_change", String.valueOf(reverseBalance));
        variables.put("$user_balance_change", String.valueOf(userBalance));

        String queryResult = this.execute(QueryFile.USER_BALANCE_UPDATE_USER_BALANCE, variables);
        UpdateUserBalanceResponse userBalanceResponse = JsonUtils.toJson(queryResult, UpdateUserBalanceResponse.class);

        if (userBalanceResponse == null || userBalanceResponse.getData() == null ||
                userBalanceResponse.getData().getItems() == null ||
                userBalanceResponse.getData().getItems().getUserId() == null)
            return null;

        return userBalanceResponse.getData().getItems();
    }

    @Override
    public UpdateBalanceAndTransactionResponse updateTransactionAndBalance(long transactionId, int status, long userId,
                                                                           double reverseAmountChange,
                                                                           double activeAmountChange, double version) {

        Map<String, String> variables = new HashMap<>();

        variables.put("$transaction_id", String.valueOf(transactionId));
        variables.put("$transaction_status", String.valueOf(status));
        variables.put("$user_id", String.valueOf(userId));
        variables.put("$reverse_balance_change", String.valueOf(reverseAmountChange));
        variables.put("$user_balance_change", String.valueOf(activeAmountChange));
        variables.put("$version", String.valueOf(version));

        String queryResult = this.execute(QueryFile.USER_BALANCE_UPDATE_USER_BALANCE_AND_TRANSACTION, variables);
        return JsonUtils.toJson(queryResult, UpdateBalanceAndTransactionResponse.class);

    }


    // reverseAmountChange < 0 ,activeAmountChange > 0
    @Override
    public UpdateBalanceAndTransactionResponse rollbackTransactionAndBalance(long transactionId, int status, long userId,
                                                                             double reverseAmountChange, double activeAmountChange, double version) {
        Map<String, String> variables = new HashMap<>();

        variables.put("$transaction_id", String.valueOf(transactionId));
        variables.put("$transaction_status", String.valueOf(status));
        variables.put("$user_id", String.valueOf(userId));
        variables.put("$reverse_balance_change", String.valueOf(reverseAmountChange));
        variables.put("$user_balance_change", String.valueOf(activeAmountChange));
        variables.put("$version", String.valueOf(version));

        String queryResult = this.execute(QueryFile.USER_BALANCE_UPDATE_USER_BALANCE_AND_TRANSACTION, variables);
        return JsonUtils.toJson(queryResult, UpdateBalanceAndTransactionResponse.class);

    }
}
