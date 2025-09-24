package com.comviva.dto;

public class ProductOrderItemDTO {

    private Long id;
    private String productOrderId;
    private String productOrderHref;
    private String orderItemId;
    private String orderItemAction;
    private String role;
    private String action;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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
}
