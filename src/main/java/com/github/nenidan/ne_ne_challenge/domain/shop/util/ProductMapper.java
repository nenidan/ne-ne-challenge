package com.github.nenidan.ne_ne_challenge.domain.shop.util;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.github.nenidan.ne_ne_challenge.domain.shop.dto.request.UpdateProductRequest;
import com.github.nenidan.ne_ne_challenge.domain.shop.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProductFromDto(UpdateProductRequest dto, @MappingTarget Product entity);
}
