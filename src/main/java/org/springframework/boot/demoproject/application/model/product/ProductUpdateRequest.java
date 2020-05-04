package org.springframework.boot.demoproject.application.model.product;

import java.math.BigDecimal;

import javax.validation.constraints.Positive;

public class ProductUpdateRequest {

    private String name;

    @Positive(message = "Price must be greater than zero!")
    private BigDecimal price;

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
}
