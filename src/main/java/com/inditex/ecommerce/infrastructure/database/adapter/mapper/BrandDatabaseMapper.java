package com.inditex.ecommerce.infrastructure.database.adapter.mapper;

import com.inditex.ecommerce.domain.model.price.Brand;
import com.inditex.ecommerce.infrastructure.database.model.price.BrandEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandDatabaseMapper {

    Brand fromEntityToDomain(BrandEntity entity);

}
