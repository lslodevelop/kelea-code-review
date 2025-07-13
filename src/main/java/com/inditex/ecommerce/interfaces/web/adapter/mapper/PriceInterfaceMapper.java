package com.inditex.ecommerce.interfaces.web.adapter.mapper;

import com.inditex.ecommerce.domain.model.prices.Currency;
import com.inditex.ecommerce.domain.model.prices.Price;
import com.inditex.ecommerce.interfaces.model.PriceDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PriceInterfaceMapper {

    @Mapping(source = "brand.id", target = "brandId")
    @Mapping(source = "currency", target = "currency", qualifiedByName = "currencyToString")
    PriceDto fromDomainToDto(Price domain);

    @Named("currencyToString")
    default String currencyToString(Currency currency) {
        return currency != null ? currency.getCode() : null;
    }

}
