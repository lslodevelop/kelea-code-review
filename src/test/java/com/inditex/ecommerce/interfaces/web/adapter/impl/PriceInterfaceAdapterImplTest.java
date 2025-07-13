package com.inditex.ecommerce.interfaces.web.adapter.impl;

import com.inditex.ecommerce.domain.model.price.Price;
import com.inditex.ecommerce.domain.ports.in.PriceServicePort;
import com.inditex.ecommerce.interfaces.model.price.PriceDto;
import com.inditex.ecommerce.interfaces.validation.DateValidator;
import com.inditex.ecommerce.interfaces.web.adapter.mapper.PriceInterfaceMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceInterfaceAdapterImplTest {

    @InjectMocks
    private PriceInterfaceAdapterImpl priceInterfaceAdapterImpl;

    @Mock
    private PriceServicePort priceServicePort;

    @Mock
    private PriceInterfaceMapper priceInterfaceMapper;

    @Mock
    private DateValidator dateValidator;

    @Test
    void getPriceTest() {
        //given
        final String applyDateAsString = "2020-06-16T21:00:00";
        final Long productId = 1L;
        final Long brandId = 2L;
        final LocalDateTime localDateTime = LocalDateTime.of(2020, 6, 16, 21, 0, 0);
        final Price price = new Price();
        final PriceDto priceDto = new PriceDto();

        when(dateValidator.validateLocalDateTimeFormat(applyDateAsString)).thenReturn(localDateTime);
        when(priceServicePort.getPrice(localDateTime, productId, brandId)).thenReturn(price);
        when(priceInterfaceMapper.fromDomainToDto(price)).thenReturn(priceDto);

        //when
        final PriceDto response = priceInterfaceAdapterImpl.getPrice(applyDateAsString, productId, brandId);

        //then
        assertThat(response).isEqualTo(priceDto);
        Mockito.verify(dateValidator).validateLocalDateTimeFormat(applyDateAsString);
        Mockito.verify(priceServicePort).getPrice(localDateTime, productId, brandId);
        Mockito.verify(priceInterfaceMapper).fromDomainToDto(price);

    }

}