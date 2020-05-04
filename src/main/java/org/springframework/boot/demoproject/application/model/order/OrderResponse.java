package org.springframework.boot.demoproject.application.model.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

    private Long id;

    private String buyerEmail;

    private List<String> products;

    private BigDecimal totalPrice;

    private LocalDateTime createdOn;

    public OrderResponse(Long id, String buyerEmail, List<String> products, BigDecimal totalPrice, LocalDateTime createdOn) {
        this.id = id;
        this.buyerEmail = buyerEmail;
        this.products = products;
        this.totalPrice = totalPrice;
        this.createdOn = createdOn;
    }

    public Long getId() {
        return id;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public List<String> getProducts() {
        return products;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }
}
