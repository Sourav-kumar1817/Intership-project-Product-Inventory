package com.comviva.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
public class ProductOffering extends PanacheEntity {

    public String externalId;
    public String href;
    public String name;
    public String referredType;

    @OneToOne(mappedBy = "productOffering")
    public Product product;
}
