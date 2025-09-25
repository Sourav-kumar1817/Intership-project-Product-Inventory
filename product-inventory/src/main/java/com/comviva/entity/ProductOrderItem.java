package com.comviva.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
public class ProductOrderItem extends PanacheEntity {

    private String productOrderId;
    private String productOrderHref;
    private String orderItemId;
    private String orderItemAction;
    private String role;
    private String action;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "product_id")
    private Product product;

    // Getters and Setters
    public String getProductOrderId() { return productOrderId; }
    public void setProductOrderId(String productOrderId) { this.productOrderId = productOrderId; }

    public String getProductOrderHref() { return productOrderHref; }
    public void setProductOrderHref(String productOrderHref) { this.productOrderHref = productOrderHref; }

    public String getOrderItemId() { return orderItemId; }
    public void setOrderItemId(String orderItemId) { this.orderItemId = orderItemId; }

    public String getOrderItemAction() { return orderItemAction; }
    public void setOrderItemAction(String orderItemAction) { this.orderItemAction = orderItemAction; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}
