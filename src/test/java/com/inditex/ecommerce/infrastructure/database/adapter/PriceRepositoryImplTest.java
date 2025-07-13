package com.inditex.ecommerce.infrastructure.database.adapter;

import com.inditex.ecommerce.domain.model.price.Price;
import com.inditex.ecommerce.infrastructure.database.adapter.mapper.PriceDatabaseMapper;
import com.inditex.ecommerce.infrastructure.database.model.price.PriceEntity;
import com.inditex.ecommerce.infrastructure.database.repository.PriceRepositoryH2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceRepositoryImplTest {

    @InjectMocks
    private PriceRepositoryImpl priceRepositoryImpl;

    @Mock
    private PriceRepositoryH2 priceRepositoryH2;

    @Mock
    private PriceDatabaseMapper priceDatabaseMapper;

    @Test
    void getPriceTest() {
        //given
        final LocalDateTime applyDate = LocalDateTime.now();
        final Long productId = 1L;
        final Long brandId = 2L;
        final PriceEntity priceEntity = new PriceEntity();
        final Optional<PriceEntity> optionalPriceEntity = Optional.of(priceEntity);

        when(priceRepositoryH2
                .findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        productId, brandId, applyDate, applyDate)).thenReturn(optionalPriceEntity);
        when(priceDatabaseMapper.fromEntityToDomain(priceEntity)).thenReturn(new Price());

        //when
        final Optional<Price> optionalPrice = priceRepositoryImpl.getPrice(applyDate, productId, brandId);

        //then
        assertThat(optionalPrice).isPresent();
        Mockito.verify(priceRepositoryH2)
                .findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        productId, brandId, applyDate, applyDate);
        Mockito.verify(priceDatabaseMapper).fromEntityToDomain(priceEntity);
    }

}