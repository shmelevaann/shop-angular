package ru.chiffa.statistic;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
//@Component
public class MethodCounterAspect {
    private final Map<String, Integer> methodCounter = new ConcurrentHashMap<>();

    @Pointcut("execution(* ru.chiffa.configs.JwtRequestFilter.*(..))")
    public void filterPointcut() {}

    @Before("execution(* ru.chiffa..*(..)) && !filterPointcut()")
    public void beforeAnyMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toLongString();
        methodCounter.put(methodName, methodCounter.getOrDefault(methodName, 0) + 1);
    }
}
