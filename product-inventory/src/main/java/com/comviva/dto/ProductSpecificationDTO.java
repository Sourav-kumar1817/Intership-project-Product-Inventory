package com.comviva.dto;

public class ProductSpecificationDTO {

    private Long id;
    private String externalId;
    private String href;
    private String version;
    private String referredType;
    private String specType;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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
}
