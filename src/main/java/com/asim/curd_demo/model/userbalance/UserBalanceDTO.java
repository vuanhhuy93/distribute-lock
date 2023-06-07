package com.asim.curd_demo.model.userbalance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserBalanceDTO {
    private long userId;
    @JsonProperty("user_balance")
    private double userBalance;

    @JsonProperty("reverse_balance")
    private double reverseBalance;

    private int version;
}
