package com.asim.curd_demo.controllers;

import com.asim.curd_demo.aop.MetricsBenchmark;
import com.asim.curd_demo.model.response.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metrics")
public class MetricsController {

    @GetMapping
    public BaseResponse<MetricsBenchmark> getMetrics(){
        BaseResponse<MetricsBenchmark> response = new BaseResponse<>();
        response.setData(MetricsBenchmark.getInstance());
        return response;
    }
}
