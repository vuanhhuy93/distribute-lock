package com.asim.curd_demo.controllers;

import com.asim.curd_demo.model.request.OrderActionRequest;
import com.asim.curd_demo.model.request.OrderRequest;
import com.asim.curd_demo.model.response.BaseResponse;
import com.asim.curd_demo.model.response.OrderResponse;
import com.asim.curd_demo.services.OrderService;
import com.asim.curd_demo.utils.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public BaseResponse<OrderResponse> createOrder(@RequestBody OrderRequest request) throws ApplicationException, Exception{

            return orderService.createOrder(request);
    }


    @PutMapping()
    public BaseResponse<OrderResponse> confirmOrCancelOrder(@RequestBody OrderActionRequest request) throws ApplicationException, Exception{

            return orderService.confirmOrCancelOrder(request.getId(), request.getAction());

    }
}
