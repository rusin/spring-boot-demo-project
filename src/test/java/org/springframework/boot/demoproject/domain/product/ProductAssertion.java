package org.springframework.boot.demoproject.domain.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductAssertion {

    public static void assertProduct(Product product, String  sku, String name, Double price, LocalDateTime createdOn) {
        assertEquals(sku, product.getSku());
        assertEquals(name, product.getName());
        assertEquals(BigDecimal.valueOf(price), product.getPrice());
        assertEquals(createdOn, product.getCreatedOn());
    }
}
