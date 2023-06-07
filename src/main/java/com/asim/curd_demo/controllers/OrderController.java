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
    public BaseResponse<OrderResponse> createOrder(@RequestBody OrderRequest request){
        BaseResponse<OrderResponse> response ;
        try {
            response = orderService.createOrder(request);
        } catch (ApplicationException ex){
            response = new BaseResponse<>();
            response.setCode(ex.getCode());
            response.setMessage(ex.getMessage());
        } catch (Exception e){
            response = new BaseResponse<>();
            response.setCode(500);
            response.setMessage("SYS -ERROR");

        }


        return response;
    }


    @PutMapping()
    public BaseResponse<OrderResponse> confirmOrCancelOrder(@RequestBody OrderActionRequest request){
        BaseResponse<OrderResponse> response ;
        try {
            response = orderService.confirmOrCancelOrder(request.getId(), request.getAction());
        } catch (ApplicationException ex){
            response = new BaseResponse<>();
            response.setCode(ex.getCode());
            response.setMessage(ex.getMessage());
        } catch (Exception e){
            response = new BaseResponse<>();
            response.setCode(500);
            response.setMessage("SYS -ERROR");

        }


        return response;
    }
}
