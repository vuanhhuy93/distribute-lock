package com.asim.curd_demo.model.warehouse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UpdateWarehouseResponse {
    private ProductWarehouseData data;
    @Data
    public static class ProductWarehouseData{

        @JsonProperty("user_db_v2_updatewarehouse")
        private ProductWarehouseDTO returning;
    }


}