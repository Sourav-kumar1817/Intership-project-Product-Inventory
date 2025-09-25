package com.comviva.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
public class ProductOffering extends PanacheEntity {

    private String externalId;
    private String href;
    private String name;
    private String referredType;

    @OneToOne(mappedBy = "productOffering")
    @JsonManagedReference
    private Product product;

    // Getters and Setters
    public String getExternalId() { return externalId; }
    public void setExternalId(String externalId) { this.externalId = externalId; }

    public String getHref() { return href; }
    public void setHref(String href) { this.href = href; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getReferredType() { return referredType; }
    public void setReferredType(String referredType) { this.referredType = referredType; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}
