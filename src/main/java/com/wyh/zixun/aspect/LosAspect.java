package com.wyh.zixun.aspect;


import com.wyh.zixun.controller.HomeController;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class LosAspect {
    private static final Logger logger = LoggerFactory.getLogger(LosAspect.class);

    @Before("execution(* com.wyh.zixun.controller.HomeController.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder();
        for (Object arg:joinPoint.getArgs()){
            sb.append(arg.toString()+'|');
        }
        logger.info(new Date().toString()+"before method:"+sb.toString());
    }

    @After("execution(* com.wyh.zixun.controller.HomeController.*(..))")
    public void afterMethod(JoinPoint joinPoint) {
        logger.info(new Date().toString()+"after method:");
    }
}