package com.asim.curd_demo.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GetOrderResponse {

    private GetOrderData data;

    @Data
    public static class GetOrderData{

        @JsonProperty("user_db_order")
        private List<OrderDTO> items;
    }
}
