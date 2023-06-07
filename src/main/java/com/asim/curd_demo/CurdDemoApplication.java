package com.asim.curd_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CurdDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurdDemoApplication.class, args);
    }

}
