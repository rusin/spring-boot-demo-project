package org.springframework.boot.demoproject.application.model.product;

import javax.validation.constraints.NotBlank;

public class ProductCreateRequest extends ProductUpdateRequest {

    @NotBlank(message = "Product sku number must be provided!")
    private String sku;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
