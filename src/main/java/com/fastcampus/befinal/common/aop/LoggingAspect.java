package com.fastcampus.befinal.common.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Value("${log.request.body:false}")
    private boolean logRequestBody;

    @Pointcut("bean(*Controller)")
    private void controller() {}

    @Pointcut("bean(*Service)")
    private void service() {}

    @Pointcut("bean(*DataProvider)")
    private void dataProvider() {}

    @Pointcut("execution(* com.fastcampus.befinal.domain.service.JwtAuthService.*(..))")
    public void jwtAuthServiceMethods() {}

    @Around("controller() || service()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        var startTime = System.currentTimeMillis();
        var methodName = joinPoint.getSignature().toShortString();
        Object result = joinPoint.proceed(joinPoint.getArgs());
        var executionTime = System.currentTimeMillis() - startTime;
        log.info("methodName = [{}], executionTime = [{}ms], response = [{}]", methodName, executionTime, result);
        return result;
    }

    @AfterThrowing(pointcut = "controller() || service() || dataProvider()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) throws Throwable {
        logException(joinPoint.getSignature().toShortString(), e);
    }


    @Around("jwtAuthServiceMethods()")
    public Object logJwtAuth(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed(joinPoint.getArgs());
        } catch (Throwable e) {
            logException(joinPoint.getSignature().toShortString(), e);
            throw e;
        }
    }

    private String paramMapToString(Map<String, String[]> paramMap) {
        return paramMap.entrySet().stream()
                .map(entry -> String.format("%s -> (%s)",
                        entry.getKey(), String.join(",", entry.getValue())))
                .collect(Collectors.joining(", "));
    }


    private void logException(String methodName, Throwable e) throws Throwable {
        String exceptionName = e.getClass().getSimpleName();
        String exceptionMessage = e.getMessage();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        String httpMethod = request.getMethod();
        String uri = request.getRequestURI();

        Map<String, String[]> paramMap = request.getParameterMap();
        String params = paramMapToString(paramMap);

        String requestBody = "";
        if (request instanceof ContentCachingRequestWrapper) {
            ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) request;
            requestBody = new String(cachingRequest.getContentAsByteArray(), StandardCharsets.UTF_8);
        }

        if (logRequestBody && "POST".equalsIgnoreCase(request.getMethod())) {
            log.error("\n[Exception] \n" +
                            "methodName = {}\n" +
                            "exceptionName = {}\n" +
                            "exceptionMessage = {}\n" +
                            "httpMethod = {}\n" +
                            "uri = {}\n" +
                            "parameters = {}\n" +
                            "requestBody = {}\n" +
                            "ip = {}\n",
                    methodName, exceptionName, exceptionMessage, httpMethod, uri, params, requestBody, ipAddress, e);
        } else {
            log.error("\n[Exception] \n" +
                            "methodName = {}\n" +
                            "exceptionName = {}\n" +
                            "exceptionMessage = {}\n" +
                            "httpMethod = {}\n" +
                            "uri = {}\n" +
                            "parameters = {}\n" +
                            "ip = {}\n",
                    methodName, exceptionName, exceptionMessage, httpMethod, uri, params, ipAddress, e);
        }
    }
}
