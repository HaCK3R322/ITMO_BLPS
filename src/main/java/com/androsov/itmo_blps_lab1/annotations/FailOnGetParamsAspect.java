package com.androsov.itmo_blps_lab1.annotations;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class FailOnGetParamsAspect {


    @Around("@annotation(FailOnGetParams)")
    public Object checkForGetParams(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof HttpServletRequest) {
                request = (HttpServletRequest) arg;
            }
        }

        if(request == null) {
            throw new IllegalStateException("Method [" + joinPoint.getSignature().toLongString() + "] is annotated with @FailOnGetParams must accept HttpServletRequest as one of its params. Tell it to your backender!");
        }

        if (!request.getParameterMap().isEmpty()) {
            return ResponseEntity.badRequest().body("GET parameters are not allowed in this request.");
        }
        return joinPoint.proceed();
    }
}




