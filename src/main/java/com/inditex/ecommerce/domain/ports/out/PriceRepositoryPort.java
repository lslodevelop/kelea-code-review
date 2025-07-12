package com.inditex.ecommerce.domain.ports.out;

import com.inditex.ecommerce.domain.model.prices.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepositoryPort {

    Optional<Price> getPrice(LocalDateTime applyDate, Long productId, Long brandId);

}
