package ru.chiffa.statistic;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
//@Component
public class ControllerTimeAspect {
    private final Map<String, Long> controllerTimeCounter = new ConcurrentHashMap<>();

    @Around("execution(* ru.chiffa.controllers..*(..))")
    public Object countTimeAroundMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String controllerName = proceedingJoinPoint.getSignature().getDeclaringTypeName();

        long begin = System.currentTimeMillis();
        Object out = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();

        controllerTimeCounter.put(controllerName, controllerTimeCounter.getOrDefault(controllerName, 0L) + end - begin);
        return out;
    }
}
