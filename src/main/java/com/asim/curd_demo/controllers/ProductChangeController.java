package com.asim.curd_demo.controllers;

import com.asim.curd_demo.model.request.ProductChangeRequest;
import com.asim.curd_demo.model.response.BaseResponse;
import com.asim.curd_demo.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product")
public class ProductChangeController {

    @Autowired
    ProductService productService;

    @PostMapping
    public BaseResponse<String> changeProductWarehouse(@RequestBody ProductChangeRequest request){

        return productService.changeProduct(request.getProductId(), request.getTotal());
    }
}
