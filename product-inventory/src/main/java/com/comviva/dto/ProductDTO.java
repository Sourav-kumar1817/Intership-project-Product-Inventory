package com.comviva.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ProductDTO {

    private Long id;
    private String description;
    private boolean isBundle;
    private boolean isCustomerVisible;
    private String name;
    private String productSerialNumber;
    private LocalDateTime startDate;
    private String status;
    private List<ProductCharacteristicDTO> characteristics;
    private List<ProductOrderItemDTO> orderItems;
    private List<RelatedPartyDTO> relatedParties;
    private List<RealizingServiceDTO> realizingServices;
    private ProductOfferingDTO productOffering;
    private ProductSpecificationDTO productSpecification;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isBundle() { return isBundle; }
    public void setBundle(boolean bundle) { isBundle = bundle; }

    public boolean isCustomerVisible() { return isCustomerVisible; }
    public void setCustomerVisible(boolean customerVisible) { isCustomerVisible = customerVisible; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getProductSerialNumber() { return productSerialNumber; }
    public void setProductSerialNumber(String productSerialNumber) { this.productSerialNumber = productSerialNumber; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<ProductCharacteristicDTO> getCharacteristics() { return characteristics; }
    public void setCharacteristics(List<ProductCharacteristicDTO> characteristics) { this.characteristics = characteristics; }

    public List<ProductOrderItemDTO> getOrderItems() { return orderItems; }
    public void setOrderItems(List<ProductOrderItemDTO> orderItems) { this.orderItems = orderItems; }

    public List<RelatedPartyDTO> getRelatedParties() { return relatedParties; }
    public void setRelatedParties(List<RelatedPartyDTO> relatedParties) { this.relatedParties = relatedParties; }

    public List<RealizingServiceDTO> getRealizingServices() { return realizingServices; }
    public void setRealizingServices(List<RealizingServiceDTO> realizingServices) { this.realizingServices = realizingServices; }

    public ProductOfferingDTO getProductOffering() { return productOffering; }
    public void setProductOffering(ProductOfferingDTO productOffering) { this.productOffering = productOffering; }

    public ProductSpecificationDTO getProductSpecification() { return productSpecification; }
    public void setProductSpecification(ProductSpecificationDTO productSpecification) { this.productSpecification = productSpecification; }
}
