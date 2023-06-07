package com.asim.curd_demo.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderDTO {
    private long id;
    @JsonProperty("product_id")
    private long productId;

    @JsonProperty("user_id")
    private long userId;

    private double amount;

    private int total;

    private int status;

    @JsonProperty("transaction_id")
    private long transactionId;
}
