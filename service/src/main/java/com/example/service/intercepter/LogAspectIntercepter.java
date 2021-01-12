package com.example.service.intercepter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class LogAspectIntercepter {
    @Pointcut("@annotation(com.example.service.annotation.LogAspect)")
    public void methodAction() {

    }

    @Around("methodAction()")
    public void afterPointCutMethod(final ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        String name = method.getName();
        String className = method.getDeclaringClass().getSimpleName();
        Object[] params = joinPoint.getArgs();
        log.info("{}.{},request:{}", className, name, JSON.toJSONString(params));
        Object result = joinPoint.proceed();
        log.info("{}.{},response:{}", className, name, JSON.toJSONString(result));

    }
}
