package com.code.repository.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
@Slf4j
public class SystemLogAop {

    @Pointcut("@annotation(com.code.repository.aop.annotation.SystemLogAnno)")
    public void activePointCut() {
    }


    @AfterReturning(value = "activePointCut()", returning = "result")
    public void doAfter(JoinPoint joinPoint, String result) {
        System.out.println("目标程序正常执行后执行");
    }

    @AfterThrowing(value = "activePointCut()", throwing = "e")
    public void doThrowing(JoinPoint joinPoint, Exception e) {
        System.out.println("目标程序异常后执行");
    }

}
