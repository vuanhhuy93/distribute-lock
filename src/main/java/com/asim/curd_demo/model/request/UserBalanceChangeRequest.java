package com.asim.curd_demo.model.request;

import lombok.Data;

@Data
public class UserBalanceChangeRequest {
    private long userId;
    private double balance;
}
