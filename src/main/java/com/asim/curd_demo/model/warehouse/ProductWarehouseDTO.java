package com.asim.curd_demo.model.warehouse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductWarehouseDTO {
    @JsonProperty("product_id")
    private long productId;

    @JsonProperty("total")
    private int total;

    @JsonProperty("active_number")
    private int activeNumber;

    @JsonProperty("reverse_number")
    private int reverse_number;

    private int version;
}