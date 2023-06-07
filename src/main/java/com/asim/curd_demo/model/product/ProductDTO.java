package com.asim.curd_demo.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ProductDTO {
    private long id;
    @JsonProperty("product_name")
    private String productName;

    private double price;

}
