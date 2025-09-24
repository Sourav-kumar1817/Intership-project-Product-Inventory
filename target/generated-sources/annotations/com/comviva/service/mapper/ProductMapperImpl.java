package com.comviva.service.mapper;

import com.comviva.dto.ProductCharacteristicDTO;
import com.comviva.dto.ProductDTO;
import com.comviva.dto.ProductOfferingDTO;
import com.comviva.dto.ProductOrderItemDTO;
import com.comviva.dto.ProductSpecificationDTO;
import com.comviva.dto.RealizingServiceDTO;
import com.comviva.dto.RelatedPartyDTO;
import com.comviva.entity.Product;
import com.comviva.entity.ProductCharacteristic;
import com.comviva.entity.ProductOffering;
import com.comviva.entity.ProductOrderItem;
import com.comviva.entity.ProductSpecification;
import com.comviva.entity.RealizingService;
import com.comviva.entity.RelatedParty;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-24T16:38:44+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (Oracle Corporation)"
)
@ApplicationScoped
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product toEntity(ProductDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Product product = new Product();

        product.setDescription( dto.getDescription() );
        product.setBundle( dto.isBundle() );
        product.setCustomerVisible( dto.isCustomerVisible() );
        product.setName( dto.getName() );
        product.setProductSerialNumber( dto.getProductSerialNumber() );
        product.setStartDate( dto.getStartDate() );
        product.setStatus( dto.getStatus() );
        product.setCharacteristics( productCharacteristicDTOListToProductCharacteristicList( dto.getCharacteristics() ) );
        product.setOrderItems( productOrderItemDTOListToProductOrderItemList( dto.getOrderItems() ) );
        product.setRelatedParties( relatedPartyDTOListToRelatedPartyList( dto.getRelatedParties() ) );
        product.setRealizingServices( realizingServiceDTOListToRealizingServiceList( dto.getRealizingServices() ) );
        product.setProductOffering( toEntity( dto.getProductOffering() ) );
        product.setProductSpecification( toEntity( dto.getProductSpecification() ) );
        product.id = dto.getId();

        return product;
    }

    @Override
    public ProductDTO toDTO(Product entity) {
        if ( entity == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setId( entity.id );
        productDTO.setDescription( entity.getDescription() );
        productDTO.setBundle( entity.isBundle() );
        productDTO.setCustomerVisible( entity.isCustomerVisible() );
        productDTO.setName( entity.getName() );
        productDTO.setProductSerialNumber( entity.getProductSerialNumber() );
        productDTO.setStartDate( entity.getStartDate() );
        productDTO.setStatus( entity.getStatus() );
        productDTO.setCharacteristics( productCharacteristicListToProductCharacteristicDTOList( entity.getCharacteristics() ) );
        productDTO.setOrderItems( productOrderItemListToProductOrderItemDTOList( entity.getOrderItems() ) );
        productDTO.setRelatedParties( relatedPartyListToRelatedPartyDTOList( entity.getRelatedParties() ) );
        productDTO.setRealizingServices( realizingServiceListToRealizingServiceDTOList( entity.getRealizingServices() ) );
        productDTO.setProductOffering( toDTO( entity.getProductOffering() ) );
        productDTO.setProductSpecification( toDTO( entity.getProductSpecification() ) );

        return productDTO;
    }

    @Override
    public ProductCharacteristic toEntity(ProductCharacteristicDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ProductCharacteristic productCharacteristic = new ProductCharacteristic();

        productCharacteristic.setName( dto.getName() );
        productCharacteristic.setValueType( dto.getValueType() );
        productCharacteristic.setValue( dto.getValue() );

        return productCharacteristic;
    }

    @Override
    public ProductCharacteristicDTO toDTO(ProductCharacteristic entity) {
        if ( entity == null ) {
            return null;
        }

        ProductCharacteristicDTO productCharacteristicDTO = new ProductCharacteristicDTO();

        productCharacteristicDTO.setName( entity.getName() );
        productCharacteristicDTO.setValueType( entity.getValueType() );
        productCharacteristicDTO.setValue( entity.getValue() );

        return productCharacteristicDTO;
    }

    @Override
    public ProductOrderItem toEntity(ProductOrderItemDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ProductOrderItem productOrderItem = new ProductOrderItem();

        productOrderItem.setProductOrderId( dto.getProductOrderId() );
        productOrderItem.setProductOrderHref( dto.getProductOrderHref() );
        productOrderItem.setOrderItemId( dto.getOrderItemId() );
        productOrderItem.setOrderItemAction( dto.getOrderItemAction() );
        productOrderItem.setRole( dto.getRole() );
        productOrderItem.setAction( dto.getAction() );
        productOrderItem.id = dto.getId();

        return productOrderItem;
    }

    @Override
    public ProductOrderItemDTO toDTO(ProductOrderItem entity) {
        if ( entity == null ) {
            return null;
        }

        ProductOrderItemDTO productOrderItemDTO = new ProductOrderItemDTO();

        productOrderItemDTO.setId( entity.id );
        productOrderItemDTO.setProductOrderId( entity.getProductOrderId() );
        productOrderItemDTO.setProductOrderHref( entity.getProductOrderHref() );
        productOrderItemDTO.setOrderItemId( entity.getOrderItemId() );
        productOrderItemDTO.setOrderItemAction( entity.getOrderItemAction() );
        productOrderItemDTO.setRole( entity.getRole() );
        productOrderItemDTO.setAction( entity.getAction() );

        return productOrderItemDTO;
    }

    @Override
    public RelatedParty toEntity(RelatedPartyDTO dto) {
        if ( dto == null ) {
            return null;
        }

        RelatedParty relatedParty = new RelatedParty();

        relatedParty.setExternalId( dto.getExternalId() );
        relatedParty.setHref( dto.getHref() );
        relatedParty.setName( dto.getName() );
        relatedParty.setRole( dto.getRole() );
        relatedParty.setReferredType( dto.getReferredType() );
        relatedParty.id = dto.getId();

        return relatedParty;
    }

    @Override
    public RelatedPartyDTO toDTO(RelatedParty entity) {
        if ( entity == null ) {
            return null;
        }

        RelatedPartyDTO relatedPartyDTO = new RelatedPartyDTO();

        relatedPartyDTO.setId( entity.id );
        relatedPartyDTO.setExternalId( entity.getExternalId() );
        relatedPartyDTO.setHref( entity.getHref() );
        relatedPartyDTO.setName( entity.getName() );
        relatedPartyDTO.setRole( entity.getRole() );
        relatedPartyDTO.setReferredType( entity.getReferredType() );

        return relatedPartyDTO;
    }

    @Override
    public RealizingService toEntity(RealizingServiceDTO dto) {
        if ( dto == null ) {
            return null;
        }

        RealizingService realizingService = new RealizingService();

        realizingService.setExternalId( dto.getExternalId() );
        realizingService.setHref( dto.getHref() );
        realizingService.setRole( dto.getRole() );
        realizingService.setReferredType( dto.getReferredType() );
        realizingService.id = dto.getId();

        return realizingService;
    }

    @Override
    public RealizingServiceDTO toDTO(RealizingService entity) {
        if ( entity == null ) {
            return null;
        }

        RealizingServiceDTO realizingServiceDTO = new RealizingServiceDTO();

        realizingServiceDTO.setId( entity.id );
        realizingServiceDTO.setExternalId( entity.getExternalId() );
        realizingServiceDTO.setHref( entity.getHref() );
        realizingServiceDTO.setRole( entity.getRole() );
        realizingServiceDTO.setReferredType( entity.getReferredType() );

        return realizingServiceDTO;
    }

    @Override
    public ProductOffering toEntity(ProductOfferingDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ProductOffering productOffering = new ProductOffering();

        productOffering.setExternalId( dto.getExternalId() );
        productOffering.setHref( dto.getHref() );
        productOffering.setName( dto.getName() );
        productOffering.setReferredType( dto.getReferredType() );
        productOffering.id = dto.getId();

        return productOffering;
    }

    @Override
    public ProductOfferingDTO toDTO(ProductOffering entity) {
        if ( entity == null ) {
            return null;
        }

        ProductOfferingDTO productOfferingDTO = new ProductOfferingDTO();

        productOfferingDTO.setId( entity.id );
        productOfferingDTO.setExternalId( entity.getExternalId() );
        productOfferingDTO.setHref( entity.getHref() );
        productOfferingDTO.setName( entity.getName() );
        productOfferingDTO.setReferredType( entity.getReferredType() );

        return productOfferingDTO;
    }

    @Override
    public ProductSpecification toEntity(ProductSpecificationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ProductSpecification productSpecification = new ProductSpecification();

        productSpecification.setExternalId( dto.getExternalId() );
        productSpecification.setHref( dto.getHref() );
        productSpecification.setVersion( dto.getVersion() );
        productSpecification.setReferredType( dto.getReferredType() );
        productSpecification.setSpecType( dto.getSpecType() );
        productSpecification.id = dto.getId();

        return productSpecification;
    }

    @Override
    public ProductSpecificationDTO toDTO(ProductSpecification entity) {
        if ( entity == null ) {
            return null;
        }

        ProductSpecificationDTO productSpecificationDTO = new ProductSpecificationDTO();

        productSpecificationDTO.setId( entity.id );
        productSpecificationDTO.setExternalId( entity.getExternalId() );
        productSpecificationDTO.setHref( entity.getHref() );
        productSpecificationDTO.setVersion( entity.getVersion() );
        productSpecificationDTO.setReferredType( entity.getReferredType() );
        productSpecificationDTO.setSpecType( entity.getSpecType() );

        return productSpecificationDTO;
    }

    @Override
    public List<ProductDTO> toDTO(List<Product> entities) {
        if ( entities == null ) {
            return null;
        }

        List<ProductDTO> list = new ArrayList<ProductDTO>( entities.size() );
        for ( Product product : entities ) {
            list.add( toDTO( product ) );
        }

        return list;
    }

    @Override
    public List<Product> toEntity(List<ProductDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<Product> list = new ArrayList<Product>( dtos.size() );
        for ( ProductDTO productDTO : dtos ) {
            list.add( toEntity( productDTO ) );
        }

        return list;
    }

    protected List<ProductCharacteristic> productCharacteristicDTOListToProductCharacteristicList(List<ProductCharacteristicDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductCharacteristic> list1 = new ArrayList<ProductCharacteristic>( list.size() );
        for ( ProductCharacteristicDTO productCharacteristicDTO : list ) {
            list1.add( toEntity( productCharacteristicDTO ) );
        }

        return list1;
    }

    protected List<ProductOrderItem> productOrderItemDTOListToProductOrderItemList(List<ProductOrderItemDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductOrderItem> list1 = new ArrayList<ProductOrderItem>( list.size() );
        for ( ProductOrderItemDTO productOrderItemDTO : list ) {
            list1.add( toEntity( productOrderItemDTO ) );
        }

        return list1;
    }

    protected List<RelatedParty> relatedPartyDTOListToRelatedPartyList(List<RelatedPartyDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<RelatedParty> list1 = new ArrayList<RelatedParty>( list.size() );
        for ( RelatedPartyDTO relatedPartyDTO : list ) {
            list1.add( toEntity( relatedPartyDTO ) );
        }

        return list1;
    }

    protected List<RealizingService> realizingServiceDTOListToRealizingServiceList(List<RealizingServiceDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<RealizingService> list1 = new ArrayList<RealizingService>( list.size() );
        for ( RealizingServiceDTO realizingServiceDTO : list ) {
            list1.add( toEntity( realizingServiceDTO ) );
        }

        return list1;
    }

    protected List<ProductCharacteristicDTO> productCharacteristicListToProductCharacteristicDTOList(List<ProductCharacteristic> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductCharacteristicDTO> list1 = new ArrayList<ProductCharacteristicDTO>( list.size() );
        for ( ProductCharacteristic productCharacteristic : list ) {
            list1.add( toDTO( productCharacteristic ) );
        }

        return list1;
    }

    protected List<ProductOrderItemDTO> productOrderItemListToProductOrderItemDTOList(List<ProductOrderItem> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductOrderItemDTO> list1 = new ArrayList<ProductOrderItemDTO>( list.size() );
        for ( ProductOrderItem productOrderItem : list ) {
            list1.add( toDTO( productOrderItem ) );
        }

        return list1;
    }

    protected List<RelatedPartyDTO> relatedPartyListToRelatedPartyDTOList(List<RelatedParty> list) {
        if ( list == null ) {
            return null;
        }

        List<RelatedPartyDTO> list1 = new ArrayList<RelatedPartyDTO>( list.size() );
        for ( RelatedParty relatedParty : list ) {
            list1.add( toDTO( relatedParty ) );
        }

        return list1;
    }

    protected List<RealizingServiceDTO> realizingServiceListToRealizingServiceDTOList(List<RealizingService> list) {
        if ( list == null ) {
            return null;
        }

        List<RealizingServiceDTO> list1 = new ArrayList<RealizingServiceDTO>( list.size() );
        for ( RealizingService realizingService : list ) {
            list1.add( toDTO( realizingService ) );
        }

        return list1;
    }
}
