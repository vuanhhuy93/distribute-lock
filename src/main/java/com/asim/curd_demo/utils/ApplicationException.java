package com.asim.curd_demo.utils;

import lombok.Data;

@Data
public class ApplicationException extends RuntimeException{
    private int code;

    public ApplicationException(int code) {
        this.code = code;
    }

    public ApplicationException(int code, String message) {
        super(message);
        this.code = code;

    }
}
