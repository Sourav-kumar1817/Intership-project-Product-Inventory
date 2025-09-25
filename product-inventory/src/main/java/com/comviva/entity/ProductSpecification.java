package com.comviva.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
public class ProductSpecification extends PanacheEntity {

    private String externalId;
    private String href;
    private String version;
    private String referredType;
    private String specType;

    @OneToOne(mappedBy = "productSpecification")
    @JsonManagedReference
    private Product product;

    // Getters and Setters
    public String getExternalId() { return externalId; }
    public void setExternalId(String externalId) { this.externalId = externalId; }

    public String getHref() { return href; }
    public void setHref(String href) { this.href = href; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public String getReferredType() { return referredType; }
    public void setReferredType(String referredType) { this.referredType = referredType; }

    public String getSpecType() { return specType; }
    public void setSpecType(String specType) { this.specType = specType; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}
