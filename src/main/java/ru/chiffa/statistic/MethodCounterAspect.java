package ru.chiffa.statistic;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
public class MethodCounterAspect {
    private final Map<String, Integer> methodCounter = new ConcurrentHashMap<>();

    @Before("execution(* ru.chiffa.*(..))")
    public void beforeAnyMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toLongString();
        methodCounter.put(methodName, methodCounter.getOrDefault(methodName, 0) + 1);
    }
}
