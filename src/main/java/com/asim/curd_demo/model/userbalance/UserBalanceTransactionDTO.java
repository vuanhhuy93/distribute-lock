package com.asim.curd_demo.model.userbalance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserBalanceTransactionDTO {
    private long id;

    @JsonProperty("user_id")
    private long userId;

    @JsonProperty("action_type")
    private int actionType;

    private double amount;

    private int status;

}
