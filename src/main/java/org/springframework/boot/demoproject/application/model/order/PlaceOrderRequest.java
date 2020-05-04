package org.springframework.boot.demoproject.application.model.order;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class PlaceOrderRequest {

    @NotBlank(message = "Buyer email must  be specified")
    private String buyerEmail;

    @NotEmpty(message = "At least one product has to be on an order list!")
    private List<String> products;

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
}
