package org.springframework.boot.demoproject.application.model.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductResponse {

    private String sku;

    private String name;

    private BigDecimal price;

    private LocalDateTime createdOn;

    public ProductResponse(String sku, String name, BigDecimal price, LocalDateTime createdOn) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.createdOn = createdOn;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }
}
