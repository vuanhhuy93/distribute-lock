package com.asim.curd_demo.services;

import com.asim.curd_demo.domains.UserBalanceDomain;
import com.asim.curd_demo.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBalanceService {

    @Autowired
    UserBalanceDomain userBalanceDomain;

    public BaseResponse<String> changeBalance(long userId, double balance){

        userBalanceDomain.createTransaction(userId, balance);

        BaseResponse<String> response = new BaseResponse<>();
        return response;
    }
}
