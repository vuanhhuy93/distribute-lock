package com.asim.curd_demo.model.userbalance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateUserBalanceResponse {
    private UserBalanceData data;

    @Data
    public static class UserBalanceData{

        @JsonProperty("user_db_v2_updateuserbalance")
        private UserBalanceDTO items;
    }
}
