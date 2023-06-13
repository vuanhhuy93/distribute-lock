package com.asim.curd_demo.model.request;

import lombok.Data;

@Data
public class ProductChangeRequest {
    private long productId;
    private long total;
}
