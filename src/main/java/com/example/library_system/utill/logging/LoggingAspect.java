package com.example.library_system.utill.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //point cut the package controller and service
    @Pointcut("execution(* com.example.library_system.controller..*(..)) || execution(* com.example.library_system.service..*(..))")
    public void applicationPackagePointcut() {}

    @Around("applicationPackagePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        logger.info("ENTER: {}() with arguments = {}", joinPoint.getSignature().toShortString(), joinPoint.getArgs());

        try {
            Object result = joinPoint.proceed();
            long elapsedTime = System.currentTimeMillis() - start;
            logger.info("EXIT: {}() with result = {}, execution time = {} ms", joinPoint.getSignature().toShortString(), result, elapsedTime);
            return result;
        } catch (Throwable e) {
            logger.error("EXCEPTION in {}() with message = {}", joinPoint.getSignature().toShortString(), e.getMessage());
            throw e;
        }
    }
}
