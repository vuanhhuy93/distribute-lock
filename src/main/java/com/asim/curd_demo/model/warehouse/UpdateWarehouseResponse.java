package com.asim.curd_demo.model.warehouse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UpdateWarehouseResponse {
    private ProductWarehouseData data;
    @Data
    public static class ProductWarehouseData{

        @JsonProperty("update_user_db_warehouse")
        private ProductWarehouseResult returning;
    }

    @Data
    public static class ProductWarehouseResult{
        @JsonProperty("returning")
        private List<ProductWarehouseDTO> items;
    }
}
