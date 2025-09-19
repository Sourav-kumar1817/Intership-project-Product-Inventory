package com.comviva.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
public class ProductSpecification extends PanacheEntity {

    public String externalId;
    public String href;
    public String version;
    public String referredType;
    public String specType;
    @OneToOne(mappedBy = "productSpecification")
    public Product product;
}
