package com.asim.curd_demo.model.response;

import lombok.Data;

@Data
public class BaseResponse <T>{
    private int code;
    private String message;
    private T data;

    public BaseResponse(){
        code = 1;
        message = "SUCCESS";
    }

    public BaseResponse(int code, String messsage){
        this.code = code;
        this.message = messsage;
    }
}
