package com.inditex.ecommerce.interfaces.web;

import com.inditex.ecommerce.interfaces.model.PriceDto;
import com.inditex.ecommerce.interfaces.web.adapter.impl.PriceInterfaceAdapterImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceV1ControllerTest {

    @InjectMocks
    private PriceV1Controller priceV1Controller;

    @Mock
    private PriceInterfaceAdapterImpl priceInterfaceAdapterImpl;

    @Test
    void getPriceTest() {
        //given
        final String applyDate = "2020-06-16T21:00:00";
        final Long productId = 1L;
        final Long brandId = 2L;
        final PriceDto priceDto = new PriceDto();

        when(priceInterfaceAdapterImpl.getPrice(applyDate, productId, brandId)).thenReturn(priceDto);

        //when
        final PriceDto response = priceV1Controller.getPrice(applyDate, productId, brandId);

        //then
        assertThat(response).isEqualTo(priceDto);
        Mockito.verify(priceInterfaceAdapterImpl).getPrice(applyDate, productId, brandId);

    }

}