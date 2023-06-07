package com.asim.curd_demo.model.userbalance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserBalanceTransactionResponse {

    private UserBalanceTransactionData data;
    @Data
    public static class UserBalanceTransactionData{

        @JsonProperty("user_db_v2_user_balance_transaction")
        private List<UserBalanceTransactionDTO> items;
    }
}
