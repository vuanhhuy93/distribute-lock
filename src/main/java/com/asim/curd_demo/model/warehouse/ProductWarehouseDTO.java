package com.asim.curd_demo.model.warehouse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductWarehouseDTO {
    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("total")
    private Integer total;

    @JsonProperty("active_number")
    private Integer activeNumber;

    @JsonProperty("reverse_number")
    private Integer reverse_number;

    private Integer version;
}