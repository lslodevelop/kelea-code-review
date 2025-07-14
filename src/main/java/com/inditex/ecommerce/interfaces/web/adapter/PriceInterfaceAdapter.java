package com.inditex.ecommerce.interfaces.web.adapter;

import com.inditex.ecommerce.interfaces.model.price.PriceDto;

public interface PriceInterfaceAdapter {

    PriceDto getPrice(String applyDateAsString, Long productId, Long brandId);

}
