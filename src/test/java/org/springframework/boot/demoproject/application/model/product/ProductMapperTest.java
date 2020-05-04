package org.springframework.boot.demoproject.application.model.product;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.demoproject.domain.product.Product;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.demoproject.ProductTestData.CREATED_ON_DATE;
import static org.springframework.boot.demoproject.ProductTestData.NAME;
import static org.springframework.boot.demoproject.ProductTestData.PRICE;
import static org.springframework.boot.demoproject.ProductTestData.SKU;
import static org.springframework.boot.demoproject.ProductTestData.createCreateProductRequest;
import static org.springframework.boot.demoproject.ProductTestData.createProduct;
import static org.springframework.boot.demoproject.ProductTestData.createUpdateProductRequest;

@ExtendWith(MockitoExtension.class)
public class ProductMapperTest {

    @InjectMocks
    private ProductMapper productMapper;

    @Test
    public void shouldMapCreateProductRequestToProduct() {
        Product product = productMapper.mapToProduct(createCreateProductRequest(SKU, NAME, PRICE));
        assertEquals(SKU, product.getSku());
        assertEquals(NAME, product.getName());
        assertEquals(BigDecimal.valueOf(PRICE), product.getPrice());
    }

    @Test
    public void shouldMapUpdateProductRequestToProduct() {
        Product product = productMapper.mapToProduct(SKU, createUpdateProductRequest(NAME, PRICE));
        assertEquals(SKU, product.getSku());
        assertEquals(NAME, product.getName());
        assertEquals(BigDecimal.valueOf(PRICE), product.getPrice());
    }

    @Test
    public void shouldProductToProductResponse() {
        ProductResponse productResponse = productMapper.mapToProductResponse(createProduct(SKU, NAME, PRICE, CREATED_ON_DATE));
        assertEquals(SKU, productResponse.getSku());
        assertEquals(NAME, productResponse.getName());
        assertEquals(BigDecimal.valueOf(PRICE), productResponse.getPrice());
        assertEquals(CREATED_ON_DATE, productResponse.getCreatedOn());
    }
}
