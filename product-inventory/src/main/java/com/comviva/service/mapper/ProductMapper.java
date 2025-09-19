package com.comviva.service.mapper;

import com.comviva.dto.*;
import com.comviva.entity.*;

import java.util.stream.Collectors;

public class ProductMapper {

    public static Product toEntity(ProductDTO dto) {
        if (dto == null) return null;
        Product product = new Product();
        product.description = dto.description;
        product.isBundle = dto.isBundle;
        product.isCustomerVisible = dto.isCustomerVisible;
        product.name = dto.name;
        product.productSerialNumber = dto.productSerialNumber;
        product.startDate = dto.startDate;
        product.status = dto.status;

        if (dto.characteristics != null) {
            product.characteristics = dto.characteristics.stream().map(c -> {
                ProductCharacteristic pc = new ProductCharacteristic();
                pc.name = c.name;
                pc.valueType = c.valueType;
                pc.value = c.value;
                pc.product = product;
                return pc;
            }).collect(Collectors.toList());
        }
        if (dto.productOffering != null) {
            ProductOffering po = new ProductOffering();
            po.name = dto.productOffering.name;
            product.productOffering = po;
        }
        if (dto.productSpecification != null) {
            ProductSpecification ps = new ProductSpecification();
            ps.specType = dto.productSpecification.specType;
            product.productSpecification = ps;
        }

        if (dto.orderItems != null) {
            product.orderItems = dto.orderItems.stream().map(o -> {
                ProductOrderItem poi = new ProductOrderItem();
                poi.action = o.action;  // ID is auto-generated
                poi.product = product;
                return poi;
            }).collect(Collectors.toList());
        }

        if (dto.relatedParties != null) {
            product.relatedParties = dto.relatedParties.stream().map(r -> {
                RelatedParty rp = new RelatedParty();
                rp.name = r.name;
                rp.role = r.role;
                rp.product = product;
                return rp;
            }).collect(Collectors.toList());
        }

        if (dto.realizingServices != null) {
            product.realizingServices = dto.realizingServices.stream().map(s -> {
                RealizingService rs = new RealizingService();
                rs.externalId = s.externalId;
                rs.href = s.href;
                rs.role = s.role;
                rs.referredType = s.referredType;
                rs.product = product;
                return rs;
            }).collect(Collectors.toList());
        }

        return product;
    }

    public static ProductDTO toDTO(Product entity) {
        if (entity == null) return null;

        ProductDTO dto = new ProductDTO();
        dto.id = entity.id;
        dto.description = entity.description;
        dto.isBundle = entity.isBundle;
        dto.isCustomerVisible = entity.isCustomerVisible;
        dto.name = entity.name;
        dto.productSerialNumber = entity.productSerialNumber;
        dto.startDate = entity.startDate;
        dto.status = entity.status;

        if (entity.characteristics != null) {
            dto.characteristics = entity.characteristics.stream().map(pc -> {
                ProductCharacteristicDTO c = new ProductCharacteristicDTO();
                c.name = pc.name;
                c.valueType = pc.valueType;
                c.value = pc.value;
                return c;
            }).collect(Collectors.toList());
        }

        if (entity.productOffering != null) {
            ProductOfferingDTO po = new ProductOfferingDTO();
            po.id = entity.productOffering.id;
            po.name = entity.productOffering.name;
            dto.productOffering = po;
        }

        if (entity.productSpecification != null) {
            ProductSpecificationDTO ps = new ProductSpecificationDTO();
            ps.id = entity.productSpecification.id;
            ps.specType = entity.productSpecification.specType;
            dto.productSpecification = ps;
        }

        if (entity.orderItems != null) {
            dto.orderItems = entity.orderItems.stream().map(poi -> {
                ProductOrderItemDTO o = new ProductOrderItemDTO();
                o.id = poi.id;
                o.action = poi.action;
                return o;
            }).collect(Collectors.toList());
        }

        if (entity.relatedParties != null) {
            dto.relatedParties = entity.relatedParties.stream().map(rp -> {
                RelatedPartyDTO r = new RelatedPartyDTO();
                r.id = rp.id;
                r.name = rp.name;
                r.role = rp.role;
                return r;
            }).collect(Collectors.toList());
        }
        if (entity.realizingServices != null) {
            dto.realizingServices = entity.realizingServices.stream().map(rs -> {
                RealizingServiceDTO s = new RealizingServiceDTO();
                s.externalId = rs.externalId;
                s.href = rs.href;
                s.role = rs.role;
                s.referredType = rs.referredType;
                return s;
            }).collect(Collectors.toList());
        }

        return dto;
    }
}
