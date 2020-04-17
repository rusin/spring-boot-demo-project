package org.springframework.boot.demoproject.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "ORDERS")
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
	
	@Column(name = "create_on", columnDefinition = "TIMESTAMP")
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

	@JsonProperty
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	@JsonIgnore
	@ApiModelProperty(hidden = true)
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	
    @PrePersist
    public void prePersist() {
        createdOn = LocalDateTime.now();
    }
}
