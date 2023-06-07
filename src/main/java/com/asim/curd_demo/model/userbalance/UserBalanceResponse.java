package com.asim.curd_demo.model.userbalance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserBalanceResponse {

    private UserBalanceData data;

    @Data
    public static class UserBalanceData{

        @JsonProperty("user_db_v2_user_balance")
        private List<UserBalanceDTO> items;
    }
}
