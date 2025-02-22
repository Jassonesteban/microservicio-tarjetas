package com.microservicio_tarjetas.api.infrastructure.adapter.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.processing.Generated;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Table("gift_cards")
public class GiftCardEntity {
    @Id
    private Long id;
    private String code;
    private BigDecimal amount;
    private String name;
    private String description;
    private String company;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    public GiftCardEntity(Long id, String code, BigDecimal amount, String name, String description, String company, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.id = id;
        this.code = code;
        this.amount = amount;
        this.name = name;
        this.description = description;
        this.company = company;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    public GiftCardEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }
}
