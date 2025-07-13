package com.inditex.ecommerce.interfaces.aop;

import com.inditex.ecommerce.domain.exception.ControlledErrorException;
import com.inditex.ecommerce.interfaces.tracing.TraceUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraceableEndpointAspectTest {

    @InjectMocks
    private TraceableEndpointAspect aspect;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private MethodSignature methodSignature;

    @Mock
    private TraceUtils traceUtils;

    @Test
    void traceExecutionTest() throws Throwable {
        // given
        final Object expectedResult = "testResult";
        final Object[] args = new Object[] {"arg1"};

        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getDeclaringType()).thenReturn(String.class);
        when(methodSignature.getName()).thenReturn("testMethod");
        when(joinPoint.getArgs()).thenReturn(args);
        when(joinPoint.proceed()).thenReturn(expectedResult);
        when(traceUtils.getCurrentTraceId()).thenReturn("1234");

        // when
        final Object result = aspect.traceExecution(joinPoint);

        // then
        assertThat(result).isEqualTo(expectedResult);
        verify(joinPoint).getSignature();
        verify(methodSignature).getDeclaringType();
        verify(methodSignature).getName();
        verify(joinPoint).getArgs();
        verify(joinPoint).proceed();
        verify(traceUtils, times(2)).getCurrentTraceId();
    }

    @Test
    void traceExecutionControlledErrorExceptionTest() throws Throwable {
        // given
        final Object[] args = new Object[] {"arg1"};

        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getDeclaringType()).thenReturn(String.class);
        when(methodSignature.getName()).thenReturn("testMethod");
        when(joinPoint.getArgs()).thenReturn(args);
        when(traceUtils.getCurrentTraceId()).thenReturn("1234");

        final ControlledErrorException exception = new ControlledErrorException(() -> "error.code", "Controlled error");
        when(joinPoint.proceed()).thenThrow(exception);

        // when
        ControlledErrorException thrown = assertThrows(ControlledErrorException.class, () ->
                aspect.traceExecution(joinPoint)
        );

        //then
        assertThat(thrown).isEqualTo(exception);
        verify(joinPoint).getSignature();
        verify(methodSignature).getDeclaringType();
        verify(methodSignature).getName();
        verify(joinPoint).getArgs();
        verify(joinPoint).proceed();
        verify(traceUtils, times(2)).getCurrentTraceId();
    }

    @Test
    void traceExecutionUnControlledErrorExceptionTest() throws Throwable {
        // given
        final Object[] args = new Object[] {};

        when(joinPoint.getSignature()).thenReturn(methodSignature);
        when(methodSignature.getDeclaringType()).thenReturn(String.class);
        when(methodSignature.getName()).thenReturn("testMethod");
        when(joinPoint.getArgs()).thenReturn(args);
        when(traceUtils.getCurrentTraceId()).thenReturn("1234");

        final RuntimeException ex = new RuntimeException("Exception test");
        when(joinPoint.proceed()).thenThrow(ex);

        // when
        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                aspect.traceExecution(joinPoint)
        );

        //then
        assertThat(thrown).isEqualTo(ex);
        verify(joinPoint).getSignature();
        verify(methodSignature).getDeclaringType();
        verify(methodSignature).getName();
        verify(joinPoint).getArgs();
        verify(joinPoint).proceed();
        verify(traceUtils, times(2)).getCurrentTraceId();
    }

}