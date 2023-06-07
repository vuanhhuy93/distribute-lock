package com.asim.curd_demo.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductResponse {

    private UserDBProduct data;
    @Data
    public static class UserDBProduct{
        @JsonProperty("user_db_v2_product")
        private List<ProductDTO> productItems;
    }
}