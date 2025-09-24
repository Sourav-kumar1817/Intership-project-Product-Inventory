package com.comviva.service.mapper;

import com.comviva.dto.*;
import com.comviva.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "cdi",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProductMapper {

    // Main Product mapping
    Product toEntity(ProductDTO dto);
    ProductDTO toDTO(Product entity);

    // Nested objects
    ProductCharacteristic toEntity(ProductCharacteristicDTO dto);
    ProductCharacteristicDTO toDTO(ProductCharacteristic entity);

    ProductOrderItem toEntity(ProductOrderItemDTO dto);
    ProductOrderItemDTO toDTO(ProductOrderItem entity);

    RelatedParty toEntity(RelatedPartyDTO dto);
    RelatedPartyDTO toDTO(RelatedParty entity);

    RealizingService toEntity(RealizingServiceDTO dto);
    RealizingServiceDTO toDTO(RealizingService entity);

    ProductOffering toEntity(ProductOfferingDTO dto);
    ProductOfferingDTO toDTO(ProductOffering entity);

    ProductSpecification toEntity(ProductSpecificationDTO dto);
    ProductSpecificationDTO toDTO(ProductSpecification entity);

    // Lists — MapStruct will automatically use the element mappings
    List<ProductDTO> toDTO(List<Product> entities);
    List<Product> toEntity(List<ProductDTO> dtos);
}
