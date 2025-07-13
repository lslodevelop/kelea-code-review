package com.inditex.ecommerce.infrastructure.database.adapter;

import com.inditex.ecommerce.domain.model.price.Price;
import com.inditex.ecommerce.domain.ports.out.PriceRepositoryPort;
import com.inditex.ecommerce.infrastructure.database.adapter.mapper.PriceDatabaseMapper;
import com.inditex.ecommerce.infrastructure.database.repository.PriceRepositoryH2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PriceRepositoryImpl implements PriceRepositoryPort {

    private final PriceRepositoryH2 priceRepositoryH2;
    private final PriceDatabaseMapper priceDatabaseMapper;

    @Override
    public Optional<Price> getPrice(final LocalDateTime applyDate, final Long productId, final Long brandId) {
        return priceRepositoryH2.findTopByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                productId, brandId, applyDate, applyDate)
                .map(priceDatabaseMapper::fromEntityToDomain);
    }
}
