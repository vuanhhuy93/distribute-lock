package com.asim.curd_demo.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Log4j2

public class ControllerAOP {

    @Pointcut("execution(* com.asim.curd_demo.controllers..*(..)))")
    protected void applicationControllerAllMethod() {
    }
    @Around("(applicationControllerAllMethod()) ")
    public Object logAspectController(ProceedingJoinPoint pjp) throws Throwable {



        MetricsBenchmark metricsBenchmark = MetricsBenchmark.getInstance();

        long startTime = System.nanoTime();
        Object result  = pjp.proceed();
        metricsBenchmark.statisticMetris(startTime, 1, "TEST");

        return result;

    }
}
