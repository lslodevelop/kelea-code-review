package com.inditex.ecommerce.infrastructure.database.adapter.mapper;

import com.inditex.ecommerce.domain.model.price.Currency;
import com.inditex.ecommerce.domain.model.price.Price;
import com.inditex.ecommerce.infrastructure.database.model.price.PriceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {BrandDatabaseMapper.class})
public interface PriceDatabaseMapper {

    @Mapping(source = "currency", target = "currency", qualifiedByName = "stringToCurrency")
    Price fromEntityToDomain(PriceEntity entity);

    @Named("stringToCurrency")
    default Currency stringToCurrency(String code) {
        return code != null ? new Currency(code) : null;
    }

}
