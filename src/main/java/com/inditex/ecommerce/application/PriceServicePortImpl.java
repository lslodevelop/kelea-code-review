package com.inditex.ecommerce.application;

import com.inditex.ecommerce.domain.exception.ApplicationErrorCodes;
import com.inditex.ecommerce.domain.exception.ControlledErrorException;
import com.inditex.ecommerce.domain.model.prices.Price;
import com.inditex.ecommerce.domain.ports.in.PriceServicePort;
import com.inditex.ecommerce.domain.ports.out.PriceRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriceServicePortImpl implements PriceServicePort {

    private final PriceRepositoryPort priceRepositoryPort;

    @Override
    @Cacheable(value = "price", key = "{#applyDate, #productId, #brandId}", unless = "#result == null")
    public Price getPrice(final LocalDateTime applyDate, final Long productId, final Long brandId) {
        return priceRepositoryPort
                .getPrice(applyDate, productId, brandId)
                .orElseThrow(() ->
                        new ControlledErrorException(ApplicationErrorCodes.NOT_FOUND_PRICE_ERROR,
                                "Price not found for provided criteria: " + "applyDate: " + applyDate.toString()
                                        + ", productId: " + productId + ", brandId: " + brandId));
    }
}
