package org.springframework.boot.demoproject.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

@Entity
public class Product {

	@Id
	@NotNull(message = "SKU may not be empty!")
	@ApiModelProperty(value = "The stock keeping unit", required = true)
	private String sku;
	
	private String name;
	
	private BigDecimal price;
	
	@Column(name = "create_on", columnDefinition = "TIMESTAMP")
	private LocalDateTime createdOn;
	
	@JsonIgnore
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
