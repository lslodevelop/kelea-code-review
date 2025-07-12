package com.inditex.ecommerce.interfaces.web.adapter.mapper;

import com.inditex.ecommerce.domain.model.prices.Brand;
import com.inditex.ecommerce.interfaces.model.BrandDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandInterfaceMapper {

    BrandDto fromDomainToDto(Brand entity);

}
