package com.comviva.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

public class ProductDTO {

    private Long id;
    private String description;
    private boolean isBundle;
    private boolean isCustomerVisible;
   @NotBlank(message = "Product name is required")
    private String name;
   @NotBlank(message = "Serial number is must")
    private String productSerialNumber;
   @FutureOrPresent(message = "date should be present or any in future")
    private LocalDateTime startDate;
    @NotBlank(message = "Required like ACTIVE or INACTIVE")
    private String status;
    @Valid
    private List<ProductCharacteristicDTO> characteristics;
    @Valid
    private List<ProductOrderItemDTO> orderItems;
    @Valid
    private List<RelatedPartyDTO> relatedParties;
    @Valid
    private List<RealizingServiceDTO> realizingServices;
    @Valid
    private ProductOfferingDTO productOffering;
    @Valid
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
