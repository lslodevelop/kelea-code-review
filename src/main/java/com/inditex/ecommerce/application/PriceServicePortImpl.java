package com.inditex.ecommerce.application;

import com.inditex.ecommerce.domain.model.prices.Price;
import com.inditex.ecommerce.domain.ports.in.PriceServicePort;
import com.inditex.ecommerce.domain.ports.out.PriceRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriceServicePortImpl implements PriceServicePort {

    private final PriceRepositoryPort priceRepositoryPort;

    @Override
    public Price getPrice(final UUID id) {
        return priceRepositoryPort.getPrice(id);
    }
}
