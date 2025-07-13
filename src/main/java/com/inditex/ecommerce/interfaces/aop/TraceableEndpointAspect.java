package com.inditex.ecommerce.interfaces.aop;

import com.inditex.ecommerce.domain.exception.ControlledErrorException;
import com.inditex.ecommerce.interfaces.tracing.TraceUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class TraceableEndpointAspect {

    private final TraceUtils traceUtils;

    @Around("@annotation(com.inditex.ecommerce.interfaces.aop.TraceableEndpoint)")
    public Object traceExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        final String methodName = methodSignature.getDeclaringType().getSimpleName() + "." + methodSignature.getName();
        final Object[] args = joinPoint.getArgs();

        log.info("Starting execution of {} with arguments: {}. Trace ID: {}", methodName, Arrays.toString(args),
                traceUtils.getCurrentTraceId());

        try {
            final Object result = joinPoint.proceed();
            long end = System.currentTimeMillis();

            log.info("Finished execution of {} with arguments: {}. Time taken: {} ms. Trace ID: {}",
                    methodName, Arrays.toString(args), end-start, traceUtils.getCurrentTraceId());
            return result;
        } catch (final ControlledErrorException ex) {
            long end = System.currentTimeMillis();
            log.warn("Controlled error in {}: {}. Time taken: {} ms. Trace ID: {}", methodName, ex.getMessage(),
                    end-start, traceUtils.getCurrentTraceId());
            throw ex;
        } catch (final Throwable ex) {
            long end = System.currentTimeMillis();
            log.warn("Exception in {}: {}. Time taken: {} ms. Trace ID: {}", methodName, ex.getMessage(), end-start,
                    traceUtils.getCurrentTraceId());
            throw ex;
        }
    }
}
