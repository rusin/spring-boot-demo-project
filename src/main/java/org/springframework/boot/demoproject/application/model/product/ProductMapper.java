package org.springframework.boot.demoproject.application.model.product;

import org.springframework.boot.demoproject.domain.product.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product mapToProduct(ProductCreateRequest productCreateRequest) {
        Product product = new Product();
        product.setSku(productCreateRequest.getSku());
        mapProductNameAndPrice(product, productCreateRequest);
        return product;
    }

    public Product mapToProduct(String productSku, ProductUpdateRequest productUpdateRequest) {
        Product product = new Product();
        product.setSku(productSku);
        mapProductNameAndPrice(product, productUpdateRequest);
        return product;
    }

    public ProductResponse mapToProductResponse(Product product) {
        return new ProductResponse(product.getSku(), product.getName(), product.getPrice(), product.getCreatedOn());
    }

    private void mapProductNameAndPrice(Product product, ProductUpdateRequest productUpdateRequest) {
        product.setName(productUpdateRequest.getName());
        product.setPrice(productUpdateRequest.getPrice());
    }
}
