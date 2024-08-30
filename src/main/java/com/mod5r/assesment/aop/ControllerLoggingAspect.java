package com.mod5r.assesment.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerLoggingAspect {

    private static final Logger logger = LogManager.getLogger(ControllerLoggingAspect.class);

    @Before("execution(* com.mod5r.assesment.controllers..*(..))")
    public void logMethodStart(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        logger.info("Starting method: " + className + "." + methodName);
    }

    @AfterReturning("execution(* com.mod5r.assesment.controllers..*(..))")
    public void logMethodFinish(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        logger.info("Successfully finished method: " + className + "." + methodName);
    }
}