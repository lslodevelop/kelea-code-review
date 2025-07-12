package com.inditex.ecommerce.interfaces.web.adapter.impl;

import com.inditex.ecommerce.domain.model.prices.Price;
import com.inditex.ecommerce.domain.ports.in.PriceServicePort;
import com.inditex.ecommerce.interfaces.model.PriceDto;
import com.inditex.ecommerce.interfaces.validation.DateValidator;
import com.inditex.ecommerce.interfaces.web.adapter.PriceInterfaceAdapter;
import com.inditex.ecommerce.interfaces.web.adapter.mapper.PriceInterfaceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class PriceInterfaceAdapterImpl implements PriceInterfaceAdapter {

    private final PriceServicePort priceServicePort;
    private final PriceInterfaceMapper priceInterfaceMapper;
    private final DateValidator dateValidator;

    @Override
    public PriceDto getPrice(final String applyDateAsString, final Long productId, final Long brandId) {
        final LocalDateTime applyDate = dateValidator.validateLocalDateTimeFormat(applyDateAsString);
        final Price price = priceServicePort.getPrice(applyDate, productId, brandId);
        return price != null ? priceInterfaceMapper.fromDomainToDto(price) : null;
    }
}
