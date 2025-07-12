package com.inditex.ecommerce.infrastructure.database.adapter;

import com.inditex.ecommerce.domain.model.prices.Price;
import com.inditex.ecommerce.domain.ports.out.PriceRepositoryPort;
import com.inditex.ecommerce.infrastructure.database.adapter.mapper.PriceDatabaseMapper;
import com.inditex.ecommerce.infrastructure.database.model.PriceEntity;
import com.inditex.ecommerce.infrastructure.database.repository.PriceRepositoryH2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PriceRepositoryImpl implements PriceRepositoryPort {

    private final PriceRepositoryH2 priceRepositoryH2;
    private final PriceDatabaseMapper priceDatabaseMapper;

    @Override
    public Price getPrice(final UUID id) {
        final PriceEntity priceEntity = priceRepositoryH2.findById(id).orElse(null);
        return priceDatabaseMapper.fromEntityToDomain(priceEntity);
    }
}
