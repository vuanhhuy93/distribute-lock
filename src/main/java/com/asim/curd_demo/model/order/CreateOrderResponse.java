package com.asim.curd_demo.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderResponse {

    private CreateOrderData data;

    @Data
    public static class CreateOrderData{
        @JsonProperty("insert_user_db_order")
        private CreateOrderReturn result;

    }

    @Data
    public static class CreateOrderReturn{

        @JsonProperty("returning")
        private List<OrderDTO> items;
    }
}
