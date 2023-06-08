package com.asim.curd_demo.model.userbalance;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserBalanceDTO {
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("user_balance")
    private Double userBalance;

    @JsonProperty("reverse_balance")
    private Double reverseBalance;

    private Integer version;
}
