package org.springframework.boot.demoproject.domain.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.PrePersist;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String buyerEmail;

    @ElementCollection
    @CollectionTable(name = "ORDER_PRODUCTS", joinColumns = @JoinColumn(name = "ORDER_ID"))
    @Column(name = "PRODUCT_ID")
    private List<String> products;

    private BigDecimal totalPrice;

    private LocalDateTime createdOn;

    public Long getId() {
        return id;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    @JsonIgnore
    public void setCreatedOn(LocalDateTime createOn) {
        this.createdOn = createOn;
    }

    @PrePersist
    protected void prePersist() {
        this.createdOn = LocalDateTime.now();
    }
}
