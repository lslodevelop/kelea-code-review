package com.inditex.ecommerce.domain.ports.in;

import com.inditex.ecommerce.domain.model.price.Price;

import java.time.LocalDateTime;

public interface PriceServicePort {

    Price getPrice(LocalDateTime applyDate, Long productId, Long brandId);

}
