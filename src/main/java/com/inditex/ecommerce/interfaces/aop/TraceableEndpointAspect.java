package com.inditex.ecommerce.interfaces.aop;

import com.inditex.ecommerce.domain.exception.ControlledErrorException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class TraceableEndpointAspect {

    @Around("@annotation(com.inditex.ecommerce.interfaces.aop.TraceableEndpoint)")
    public Object traceExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final String methodName = methodSignature.getDeclaringType().getSimpleName() + "." + methodSignature.getName();
        final Object[] args = joinPoint.getArgs();

        log.info("Starting execution of {} with arguments: {}", methodName, Arrays.toString(args));

        try {
            final Object result = joinPoint.proceed();
            long end = System.currentTimeMillis();

            log.info("Finished execution of {} with arguments: {}. Time taken: {} ms", methodName, Arrays.toString(args),
                    end-start);
            return result;
        } catch (final ControlledErrorException ex) {
            long end = System.currentTimeMillis();
            log.warn("Controlled error in {}: {}. Time taken: {} ms", methodName, ex.getMessage(), end-start);
            throw ex;
        } catch (final Throwable ex) {
            long end = System.currentTimeMillis();
            log.warn("Exception in {}: {}. Time taken: {} ms", methodName, ex.getMessage(), end-start);
            throw ex;
        }
    }
}
