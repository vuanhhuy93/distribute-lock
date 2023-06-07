package com.asim.curd_demo.model.warehouse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductWarehouseResponse {
    private ProductWarehouseData data;
    @Data
    public static class ProductWarehouseData{

        @JsonProperty("user_db_v2_warehouse")
        private List<ProductWarehouseDTO> items;
    }
}
