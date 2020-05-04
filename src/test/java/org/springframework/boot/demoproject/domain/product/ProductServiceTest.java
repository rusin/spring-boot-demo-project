package org.springframework.boot.demoproject.domain.product;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.demoproject.domain.product.exception.ProductAlreadyExistsException;
import org.springframework.boot.demoproject.domain.product.exception.ProductNotFoundException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.boot.demoproject.ProductTestData.CREATED_ON_DATE;
import static org.springframework.boot.demoproject.ProductTestData.NAME;
import static org.springframework.boot.demoproject.ProductTestData.NAME_ALTERNATIVE;
import static org.springframework.boot.demoproject.ProductTestData.PRICE;
import static org.springframework.boot.demoproject.ProductTestData.PRICE_ALTERNATIVE;
import static org.springframework.boot.demoproject.ProductTestData.SKU;
import static org.springframework.boot.demoproject.ProductTestData.SKU_ALTERNATIVE;
import static org.springframework.boot.demoproject.ProductTestData.createProduct;
import static org.springframework.boot.demoproject.domain.product.ProductAssertion.assertProduct;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void shouldGetProducts() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(createProduct(SKU, NAME, PRICE, CREATED_ON_DATE),
                createProduct(SKU_ALTERNATIVE, NAME_ALTERNATIVE, PRICE_ALTERNATIVE, CREATED_ON_DATE)));

        List<Product> products = productService.getProducts();
        assertNotNull(products);
        assertEquals(2, products.size());
        assertProduct(products.get(0), SKU, NAME, PRICE, CREATED_ON_DATE);
        assertProduct(products.get(1), SKU_ALTERNATIVE, NAME_ALTERNATIVE, PRICE_ALTERNATIVE, CREATED_ON_DATE);
    }

    @Test
    public void shouldCreateProduct() {
        when(productRepository.existsById(SKU)).thenReturn(Boolean.FALSE);
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product product = (Product) invocation.getArguments()[0];
            product.setCreatedOn(CREATED_ON_DATE);
            return product;
        });

        Product newProduct = productService.createProduct(createProduct(SKU, NAME, PRICE));
        assertProduct(newProduct, SKU, NAME, PRICE, CREATED_ON_DATE);
    }

    @Test
    public void shouldNotCreateProductDuplicate() {
        when(productRepository.existsById(SKU)).thenReturn(Boolean.TRUE);
        ProductAlreadyExistsException exception = assertThrows(ProductAlreadyExistsException.class,
                () -> productService.createProduct(createProduct(SKU, NAME, PRICE)));
        assertEquals("Product with SKU " + SKU + " already exists.", exception.getMessage());
    }

    @Test
    public void shouldUpdateProduct() {
        when(productRepository.findById(SKU)).thenReturn(Optional.of(createProduct(SKU, NAME, PRICE, CREATED_ON_DATE)));
        Product updatedProduct = productService.updateProduct(createProduct(SKU, NAME_ALTERNATIVE, PRICE_ALTERNATIVE));
        assertProduct(updatedProduct, SKU, NAME_ALTERNATIVE, PRICE_ALTERNATIVE, CREATED_ON_DATE);
    }

    @Test
    public void shouldFailOnUpdatingNonExistingProduct() {
        when(productRepository.findById(SKU)).thenReturn(Optional.empty());
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> productService.updateProduct(createProduct(SKU, NAME_ALTERNATIVE, PRICE_ALTERNATIVE)));
        assertEquals("Product with SKU " + SKU + " not found", exception.getMessage());
    }

    @Test
    public void shouldDeleteProduct() {
        when(productRepository.existsById(SKU)).thenReturn(Boolean.TRUE);
        productService.deleteProduct(SKU);
        verify(productRepository).deleteById(SKU);
    }

    @Test
    public void shouldFailedOnDeleteNonExistingProduct() {
        when(productRepository.existsById(SKU)).thenReturn(Boolean.FALSE);
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () ->  productService.deleteProduct(SKU));
        assertEquals("Product with SKU " + SKU + " not found", exception.getMessage());
    }
}
