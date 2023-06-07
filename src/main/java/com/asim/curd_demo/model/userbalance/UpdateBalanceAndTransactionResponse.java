package com.asim.curd_demo.model.userbalance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UpdateBalanceAndTransactionResponse {


    private ResponseData data;
    @Data
    public static class ResponseData{
        @JsonProperty("update_user_db_v2_user_balance")
        private UpdateUserBalanceData userBalanceData;

        @JsonProperty("update_user_db_v2_user_balance_transaction")
        private UpdateTransactionData transactionData;
    }

    @Data
    public static class UpdateTransactionData{
        @JsonProperty("returning")
        private List<UserBalanceTransactionDTO> items;
    }
    @Data
    public static class UpdateUserBalanceData{

        @JsonProperty("returning")
        private List<UserBalanceDTO> items;

    }
}
