package com.comviva.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
public class RealizingService extends PanacheEntity {
    public String externalId;
    public String href;
    public String role;
    public String referredType;
    @ManyToOne
    @JoinColumn(name = "product_id")
    public Product product;
}
