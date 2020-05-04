package org.springframework.boot.demoproject.domain.product;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.demoproject.ProductTestData.CREATED_ON_DATE;
import static org.springframework.boot.demoproject.ProductTestData.NAME;
import static org.springframework.boot.demoproject.ProductTestData.NAME_ALTERNATIVE;
import static org.springframework.boot.demoproject.ProductTestData.PRICE;
import static org.springframework.boot.demoproject.ProductTestData.PRICE_ALTERNATIVE;
import static org.springframework.boot.demoproject.ProductTestData.SKU;
import static org.springframework.boot.demoproject.ProductTestData.SKU_ALTERNATIVE;
import static org.springframework.boot.demoproject.domain.product.ProductAssertion.assertProduct;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("dev")
@Sql(scripts={"classpath:jpa-test-data.sql"})
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void shouldFindAllProducts() {
        List<Product> products = productRepository.findAll();
        assertNotNull(products, "FindAll should return product data!");
        assertEquals(2, products.size());
        assertProduct(products.get(0), SKU, NAME, PRICE, CREATED_ON_DATE);
        assertProduct(products.get(1), SKU_ALTERNATIVE, NAME_ALTERNATIVE, PRICE_ALTERNATIVE, CREATED_ON_DATE);
    }

    @Test
    public void shouldFindProductById() {
        Optional<Product> productOptional = productRepository.findById(SKU);
        assertTrue(productOptional.isPresent());
        assertProduct(productOptional.get(), SKU, NAME, PRICE, CREATED_ON_DATE);
    }

    @Test
    public void shouldNotFindDeletedProductById() {
        Optional<Product> productOptional = productRepository.findById("12-201957X");
        assertFalse(productOptional.isPresent());
    }


    @Test
    public void shouldNotFindNonExistedProductById() {
        Optional<Product> productOptional = productRepository.findById("XX-201957X");
        assertFalse(productOptional.isPresent());
    }

    @Test
    public void shouldExistById() {
        assertTrue(productRepository.existsById(SKU));
    }

    @Test
    public void shouldBeDeleted() {
        assertTrue(productRepository.existsById(SKU));

        productRepository.deleteById(SKU);
        assertFalse(productRepository.existsById(SKU));
    }
}
