package com.inditex.ecommerce.domain.ports.out;

import com.inditex.ecommerce.domain.model.prices.Price;

import java.util.UUID;

public interface PriceRepositoryPort {

    Price getPrice(UUID id);

}
