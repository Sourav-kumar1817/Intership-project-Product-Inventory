package com.comviva.dto;

public class RealizingServiceDTO {

    private Long id;
    private String externalId;
    private String href;
    private String role;
    private String referredType;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getExternalId() { return externalId; }
    public void setExternalId(String externalId) { this.externalId = externalId; }

    public String getHref() { return href; }
    public void setHref(String href) { this.href = href; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getReferredType() { return referredType; }
    public void setReferredType(String referredType) { this.referredType = referredType; }
}
