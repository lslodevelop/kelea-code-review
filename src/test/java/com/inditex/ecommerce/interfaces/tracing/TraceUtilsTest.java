package com.inditex.ecommerce.interfaces.tracing;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraceUtilsTest {

    @InjectMocks
    private TraceUtils traceUtils;

    @Mock
    private Tracer tracer;

    @Test
    void getCurrentTraceIdTest() {
        //given
        final Span span = Mockito.mock(Span.class);
        final TraceContext traceContext = Mockito.mock(TraceContext.class);
        final String traceId = "1234";

        when(span.context()).thenReturn(traceContext);
        when(traceContext.traceId()).thenReturn(traceId);
        when(tracer.currentSpan()).thenReturn(span);

        //when
        final String trace = traceUtils.getCurrentTraceId();

        //then
        Assertions.assertThat(trace).isEqualTo(traceId);
        verify(span).context();
        verify(traceContext).traceId();
        verify(tracer, times(2)).currentSpan();

    }

    @Test
    void getCurrentTraceIdNullTest() {
        //given
        when(tracer.currentSpan()).thenReturn(null);

        //when
        final String trace = traceUtils.getCurrentTraceId();

        //then
        Assertions.assertThat(trace).isEqualTo("N/A");
        verify(tracer).currentSpan();
    }

}