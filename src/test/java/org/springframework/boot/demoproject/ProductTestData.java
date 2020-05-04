package org.springframework.boot.demoproject;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.demoproject.application.model.product.ProductCreateRequest;
import org.springframework.boot.demoproject.application.model.product.ProductUpdateRequest;
import org.springframework.boot.demoproject.domain.product.Product;

public class ProductTestData {
    public static final String SKU = "12-201957";
    public static final String NAME = "New Product name";
    public static final Double PRICE = 3.99;

    public static final String SKU_ALTERNATIVE = "15-032179";
    public static final String NAME_ALTERNATIVE = "Change product";
    public static final Double PRICE_ALTERNATIVE = 5.77;


    public static final String CREATED_ON_DATE_TEXT = "2020-05-01T12:15:57";

    public static final String CREATED_ON_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    public static final LocalDateTime CREATED_ON_DATE = LocalDateTime.parse(CREATED_ON_DATE_TEXT,
            DateTimeFormatter.ofPattern(CREATED_ON_DATE_PATTERN));

    public static final Product createProduct(String sku, String name, Double price) {
        return createProduct(sku, name, price, null);
    }

    public static final Product createProduct(String sku, String name, Double price, LocalDateTime createdOn) {
        Product product = new Product();
        product.setSku(sku);
        product.setName(name);
        product.setPrice(BigDecimal.valueOf(price));
        product.setCreatedOn(createdOn);
        return product;
    }

    public static ProductCreateRequest createCreateProductRequest(String sku, String name, Double price) {
        ProductCreateRequest productCreateRequest = new ProductCreateRequest();
        productCreateRequest.setSku(sku);
        productCreateRequest.setName(name);
        productCreateRequest.setPrice(BigDecimal.valueOf(price));
        return productCreateRequest;
    }

    public static String createCreateProductRequestJSON(String sku, String name, Double price) {
        ProductCreateRequest productCreateRequest = createCreateProductRequest(sku, name, price);
        return toJson(productCreateRequest);
    }

    public static ProductUpdateRequest createUpdateProductRequest(String name, Double price) {
        ProductUpdateRequest productUpdateRequest = new ProductUpdateRequest();
        productUpdateRequest.setName(name);
        productUpdateRequest.setPrice(BigDecimal.valueOf(price));
        return productUpdateRequest;
    }

    public static String createUpdateProductRequestJSON(String name, Double price) {
        ProductUpdateRequest productUpdateRequest = createUpdateProductRequest(name, price);
        return toJson(productUpdateRequest);
    }

    private static String toJson(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Test object could not be mapped to JSON!");
        }
    }
}
