package com.asim.curd_demo.model.response;

import lombok.Data;

@Data
public class OrderResponse {
    private long orderId;
    private int orderStatus;
    private String message;
}
