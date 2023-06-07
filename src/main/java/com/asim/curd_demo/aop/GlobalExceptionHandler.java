package com.asim.curd_demo.aop;

import com.asim.curd_demo.model.response.BaseResponse;
import com.asim.curd_demo.utils.ApplicationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<BaseResponse<?>> handleInternalException(Exception ex) {
        try {
            LOGGER.error("Exception ", ex);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(new BaseResponse<>(500, "SYS ERROR"), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(ApplicationException.class)
    @ResponseBody
    public ResponseEntity<BaseResponse<?>> handleApplicationException(ApplicationException ex) {

        LOGGER.error("Exception ", ex);
        return new ResponseEntity<>(new BaseResponse<>(ex.getCode(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }


}
