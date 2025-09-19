package com.comviva.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
public class ProductCharacteristic extends PanacheEntity {
    public String name;
    public String valueType;
    public String value;
    @ManyToOne
    @JoinColumn(name = "product_id")
    public Product product;
}
