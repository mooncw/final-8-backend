package com.fastcampus.befinal.common.aop;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class LoggingAspect {
    private final static Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(com.fastcampus.befinal..*)")
    public void pointcut() {}

    @AfterThrowing(pointcut = "pointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        String methodName = joinPoint.getSignature().toShortString();
        String exceptionName = e.getClass().getSimpleName();
        String exceptionMessage = e.getMessage();

        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        String ipAddress = request.getHeader("X-Forwarded-For");
        if(ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        logger.error("[Exception] {}, exceptionName = {}, exceptionMessage = {}, IP : {}",
                methodName, exceptionName, exceptionMessage, ipAddress, e);
    }

    @Around("pointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        var startTime = System.currentTimeMillis();
        var methodName = joinPoint.getSignature().toShortString();
        Object result = joinPoint.proceed(joinPoint.getArgs());
        var executionTime = System.currentTimeMillis() - startTime;
        logger.info("methodName : [{}], Execution Time : [{}ms], Response : [{}]", methodName, executionTime, result);
        return result;
    }
}
