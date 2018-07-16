package ru.homework.benchmark;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BenchmarkAspect {
	
    @Around("within(ru.homework.reader..*)") 
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    	Object result = null;
        System.out.println("method <<" + joinPoint.getSignature().getName() + ">> begin");
        long startTime = System.currentTimeMillis();
        result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        System.out.println("method <<" + joinPoint.getSignature().getName() + ">> total execution time = " + (endTime-startTime) + "ms");
        System.out.println("method <<" + joinPoint.getSignature().getName() + ">> end");       
        return result;
    }
    
}
