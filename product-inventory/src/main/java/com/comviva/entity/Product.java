package com.comviva.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Product extends PanacheEntity {

    private String description;
    private boolean isBundle;
    private boolean isCustomerVisible;
    private String name;
    private String productSerialNumber;
    private LocalDateTime startDate;
    private String status;

    @OneToMany(mappedBy="product", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<ProductCharacteristic> characteristics;

    @OneToMany(mappedBy="product", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<ProductOrderItem> orderItems;

    @OneToMany(mappedBy="product", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<RelatedParty> relatedParties;

    @OneToMany(mappedBy="product", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<RealizingService> realizingServices;

    @OneToOne(cascade = CascadeType.ALL)
    private ProductOffering productOffering;

    @OneToOne(cascade = CascadeType.ALL)
    private ProductSpecification productSpecification;

    // Getters and Setters
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean getIsBundle() { return isBundle; }
    public void setIsBundle(boolean isBundle) { this.isBundle = isBundle; }

    public boolean getIsCustomerVisible() { return isCustomerVisible; }
    public void setIsCustomerVisible(boolean isCustomerVisible) { this.isCustomerVisible = isCustomerVisible; }
    public boolean isBundle() { return isBundle; }
    public void setBundle(boolean isBundle) { this.isBundle = isBundle; }

    public boolean isCustomerVisible() { return isCustomerVisible; }
    public void setCustomerVisible(boolean isCustomerVisible) { this.isCustomerVisible = isCustomerVisible; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getProductSerialNumber() { return productSerialNumber; }
    public void setProductSerialNumber(String productSerialNumber) { this.productSerialNumber = productSerialNumber; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<ProductCharacteristic> getCharacteristics() { return characteristics; }
    public void setCharacteristics(List<ProductCharacteristic> characteristics) { this.characteristics = characteristics; }

    public List<ProductOrderItem> getOrderItems() { return orderItems; }
    public void setOrderItems(List<ProductOrderItem> orderItems) { this.orderItems = orderItems; }

    public List<RelatedParty> getRelatedParties() { return relatedParties; }
    public void setRelatedParties(List<RelatedParty> relatedParties) { this.relatedParties = relatedParties; }

    public List<RealizingService> getRealizingServices() { return realizingServices; }
    public void setRealizingServices(List<RealizingService> realizingServices) { this.realizingServices = realizingServices; }

    public ProductOffering getProductOffering() { return productOffering; }
    public void setProductOffering(ProductOffering productOffering) { this.productOffering = productOffering; }

    public ProductSpecification getProductSpecification() { return productSpecification; }
    public void setProductSpecification(ProductSpecification productSpecification) { this.productSpecification = productSpecification; }
}
