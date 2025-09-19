package com.comviva.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Product extends PanacheEntity {

    public String description;
    public boolean isBundle;
    public boolean isCustomerVisible;
    public String name;
    public String productSerialNumber;
    public LocalDateTime startDate;
    public String status;

    @OneToMany(mappedBy="product",cascade=CascadeType.ALL,orphanRemoval = true)
    public List<ProductCharacteristic> characteristics;
    @OneToMany(mappedBy ="product",cascade=CascadeType.ALL,orphanRemoval = true)
    public List<ProductOrderItem> orderItems;
    @OneToMany(mappedBy = "product",cascade=CascadeType.ALL,orphanRemoval = true)
    public List<RelatedParty> relatedParties;
    @OneToMany(mappedBy = "product",cascade=CascadeType.ALL,orphanRemoval = true)
    public List<RealizingService>realizingServices;
    @OneToOne(cascade = CascadeType.ALL)
    public ProductOffering productOffering;
    @OneToOne(cascade = CascadeType.ALL)
    public ProductSpecification productSpecification;
}
