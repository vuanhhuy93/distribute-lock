package com.asim.curd_demo.model.request;

import lombok.Data;

@Data
public class OrderActionRequest {
    private long id;
    private int action;
}
