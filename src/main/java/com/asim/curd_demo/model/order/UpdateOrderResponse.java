package com.asim.curd_demo.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UpdateOrderResponse {

    private UpdateOrderData data;
    @Data
    public static class UpdateOrderData{

        @JsonProperty("update_user_db_v2_order")
        private UpdateOrderReturning data;
    }

    @Data
    public static class UpdateOrderReturning{

        @JsonProperty("returning")
        private List<OrderDTO> items;
    }
}
