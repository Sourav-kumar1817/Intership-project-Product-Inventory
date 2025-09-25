package com.comviva.dto;

import jakarta.validation.constraints.NotBlank;

public class ProductCharacteristicDTO {
    @NotBlank(message = "Characteristic required")
    private String name;
    @NotBlank(message = "Type of value")
    private String valueType;
    @NotBlank(message = "characteristic value must")
    private String value;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getValueType() { return valueType; }
    public void setValueType(String valueType) { this.valueType = valueType; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}
