package com.comviva.dto;

import java.time.LocalDateTime;
import java.util.List;
public class ProductDTO {
    public Long id;
    public String description;
    public boolean isBundle;
    public boolean isCustomerVisible;
    public String name;
    public String productSerialNumber;
    public LocalDateTime startDate;
    public String status;

    public List<ProductCharacteristicDTO> characteristics;
    public List<ProductOrderItemDTO> orderItems;
    public List<RelatedPartyDTO> relatedParties;
    public List<RealizingServiceDTO> realizingServices;

    public ProductOfferingDTO productOffering;
    public ProductSpecificationDTO productSpecification;

}
