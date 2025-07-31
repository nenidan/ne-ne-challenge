package com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.github.nenidan.ne_ne_challenge.domain.shop.product.applicaion.dto.UpdateProductRequest;
import com.github.nenidan.ne_ne_challenge.domain.shop.product.domain.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProductFromDto(UpdateProductRequest dto, @MappingTarget Product entity);
}
