package com.comviva.entity;

import com.comviva.entity.Product;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
public class ProductOrderItem extends PanacheEntity {
    public String productOrderId;
    public String productOrderHref;
    public String orderItemId;
    public String orderItemAction;
    public String role;
    @ManyToOne
    @JoinColumn(name = "product_id")
    public Product product;
    public String action;
}
