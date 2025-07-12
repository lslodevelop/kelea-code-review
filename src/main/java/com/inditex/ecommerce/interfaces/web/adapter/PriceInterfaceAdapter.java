package com.inditex.ecommerce.interfaces.web.adapter;

import com.inditex.ecommerce.interfaces.model.PriceDto;

import java.util.UUID;

public interface PriceInterfaceAdapter {

    PriceDto getPrice(UUID id);

}
