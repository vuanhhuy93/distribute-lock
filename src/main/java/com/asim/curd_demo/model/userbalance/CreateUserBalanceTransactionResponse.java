package com.asim.curd_demo.model.userbalance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CreateUserBalanceTransactionResponse {

    private UserBalanceTransactionData data;
    @Data
    public static class UserBalanceTransactionData{

        @JsonProperty("insert_user_db_user_balance_transaction")
        private UserBalanceTransactionReturn item;
    }

    @Data
    public static class UserBalanceTransactionReturn{

        @JsonProperty("returning")
        private List<UserBalanceTransactionDTO> items;
    }
}
