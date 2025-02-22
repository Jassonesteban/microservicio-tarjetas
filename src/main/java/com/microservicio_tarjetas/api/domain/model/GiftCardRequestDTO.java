package com.microservicio_tarjetas.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

public class GiftCardRequestDTO {
    private String code;
    private BigDecimal amount;
    private String name;
    private String description;
    private String company;

    public GiftCardRequestDTO(String code, BigDecimal amount, String name, String description, String company) {
        this.code = code;
        this.amount = amount;
        this.name = name;
        this.description = description;
        this.company = company;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
