package com.inditex.ecommerce.domain.ports.in;

import com.inditex.ecommerce.domain.model.prices.Price;

import java.util.UUID;

public interface PriceServicePort {

    Price getPrice(UUID id);

}
