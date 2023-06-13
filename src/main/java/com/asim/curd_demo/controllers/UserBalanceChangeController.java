package com.asim.curd_demo.controllers;

import com.asim.curd_demo.model.request.UserBalanceChangeRequest;
import com.asim.curd_demo.model.response.BaseResponse;
import com.asim.curd_demo.services.UserBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-balance")
public class UserBalanceChangeController {

    @Autowired
    UserBalanceService userBalanceService;


    @PostMapping
    public BaseResponse<String> userBalanceChange(@RequestBody UserBalanceChangeRequest request){

        return userBalanceService.changeBalance(request.getUserId(), request.getBalance());
    }

}
