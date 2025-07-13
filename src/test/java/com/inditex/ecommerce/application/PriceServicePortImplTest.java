package com.inditex.ecommerce.application;

import com.inditex.ecommerce.domain.exception.ApplicationErrorCodes;
import com.inditex.ecommerce.domain.exception.ControlledErrorException;
import com.inditex.ecommerce.domain.model.prices.Price;
import com.inditex.ecommerce.domain.ports.out.PriceRepositoryPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceServicePortImplTest {

    @InjectMocks
    private PriceServicePortImpl priceServicePortImpl;

    @Mock
    private PriceRepositoryPort priceRepositoryPort;

    @Test
    void getPriceTest() {
        //given
        final LocalDateTime applyDate = LocalDateTime.now();
        final Long productId = 1L;
        final Long brandId = 2L;
        final Price price = new Price();
        final Optional<Price> optionalPrice = Optional.of(price);

        when(priceRepositoryPort.getPrice(applyDate, productId, brandId)).thenReturn(optionalPrice);

        //when
        final Price response = priceServicePortImpl.getPrice(applyDate, productId, brandId);

        //then
        Assertions.assertThat(response).isEqualTo(price);
        verify(priceRepositoryPort).getPrice(applyDate, productId, brandId);
    }

    @Test
    void getPrice_notFoundExceptionTest() {
        //given
        final LocalDateTime applyDate = LocalDateTime.now();
        final Long productId = 1L;
        final Long brandId = 2L;

        when(priceRepositoryPort.getPrice(applyDate, productId, brandId)).thenReturn(Optional.empty());

        //when
        final ControlledErrorException response =
                assertThrows(ControlledErrorException.class,
                        () -> priceServicePortImpl.getPrice(applyDate, productId, brandId));

        //then
        Assertions.assertThat(response.getMessage()).contains("Price not found for provided criteria");
        Assertions.assertThat(response.getErrorCode()).isEqualTo(ApplicationErrorCodes.NOT_FOUND_PRICE_ERROR);
        verify(priceRepositoryPort).getPrice(applyDate, productId, brandId);
    }

}