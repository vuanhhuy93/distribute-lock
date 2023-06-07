package com.asim.curd_demo.model.request;

import lombok.Data;

@Data
public class OrderRequest {
    private long userId;
    private long productId;
    private int total;
}
