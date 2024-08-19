package com.fastcampus.befinal.common.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

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
        log.error("[Exception] {}, exceptionName = {}, exceptionMessage = {}, IP : {}",
                methodName, exceptionName, exceptionMessage, ipAddress, e);
    }

    @Around("pointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        var startTime = System.currentTimeMillis();
        var methodName = joinPoint.getSignature().toShortString();
        Object result = joinPoint.proceed(joinPoint.getArgs());
        var executionTime = System.currentTimeMillis() - startTime;
        log.info("methodName : [{}], Execution Time : [{}ms], Response : [{}]", methodName, executionTime, result);
        return result;
    }
}
