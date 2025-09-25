package com.comviva.dto;

import jakarta.validation.constraints.NotBlank;

public class RelatedPartyDTO {

    private Long id;
    private String externalId;
    private String href;
    @NotBlank(message = "name must")
    private String name;
    @NotBlank(message = "role should be described")
    private String role;
    private String referredType;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getExternalId() { return externalId; }
    public void setExternalId(String externalId) { this.externalId = externalId; }

    public String getHref() { return href; }
    public void setHref(String href) { this.href = href; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getReferredType() { return referredType; }
    public void setReferredType(String referredType) { this.referredType = referredType; }
}
