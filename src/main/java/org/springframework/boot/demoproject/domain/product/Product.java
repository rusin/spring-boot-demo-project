package org.springframework.boot.demoproject.domain.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

@Entity
public class Product {

    @Id
    private String sku;

    private String name;

    private BigDecimal price;

    private LocalDateTime createdOn;

    private Boolean isDeleted = Boolean.FALSE;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createOn) {
        this.createdOn = createOn;
    }

    @PrePersist
    protected void prePersist() {
        this.createdOn = LocalDateTime.now();
    }
}
