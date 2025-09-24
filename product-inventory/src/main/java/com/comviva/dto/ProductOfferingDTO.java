package com.comviva.dto;

public class ProductOfferingDTO {

    private Long id;
    private String externalId;
    private String href;
    private String name;
    private String referredType;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getExternalId() { return externalId; }
    public void setExternalId(String externalId) { this.externalId = externalId; }

    public String getHref() { return href; }
    public void setHref(String href) { this.href = href; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getReferredType() { return referredType; }
    public void setReferredType(String referredType) { this.referredType = referredType; }
}
