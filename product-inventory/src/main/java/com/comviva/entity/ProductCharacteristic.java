package com.comviva.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
public class ProductCharacteristic extends PanacheEntity {

    private String name;
    private String valueType;
    private String value;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getValueType() { return valueType; }
    public void setValueType(String valueType) { this.valueType = valueType; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}
