package com.androsov.itmo_blps.annotations;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

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




