package rg.springframework.boot.demoproject.api;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.demoproject.api.ProductApi;
import org.springframework.boot.demoproject.exception.ProductAlreadyExistsException;
import org.springframework.boot.demoproject.exception.ProductNotFoundException;
import org.springframework.boot.demoproject.model.Product;
import org.springframework.boot.demoproject.peristence.ProductRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ProductApi.class)
public class ProductApiTest {
	
	private static final String PRODUCT_SKU = "12-505";
	private static final String PRODUCT_NAME = "Swatch something new";
	private static final Double PRODUCT_PRICE = 356d;
	private static final String PRODUCT_NEW_NAME = "Swatch something from Swiss";
	private static final Double PRODUCT_NEW_PRICE = 97d;
	
	@MockBean
	private ProductRepository productRepository;
	
	@Autowired
	private ProductApi productApi;
	
	@Test
	public void shouldCreateProduct() {
		Product product = TestData.createProduct(PRODUCT_SKU, PRODUCT_NAME, PRODUCT_PRICE);
		when(productRepository.save(any(Product.class))).thenReturn(product);
		when(productRepository.existsById(anyString())).thenReturn(Boolean.FALSE);
		Product newProduct = productApi.createProduct(product);
		assertEquals(product, newProduct);
	}
	
	@Test
	public void shouldFailOnCreateProductDuplicate() {
		Product product = TestData.createProduct(PRODUCT_SKU, PRODUCT_NAME, PRODUCT_PRICE);
		when(productRepository.existsById(anyString())).thenReturn(Boolean.TRUE);
		ProductAlreadyExistsException exception = assertThrows(ProductAlreadyExistsException.class, 
				() -> productApi.createProduct(product));
	}
	
	@Test
	public void shouldReturnProductList() {
		List<Product> productList = Arrays.asList(TestData.createProduct(PRODUCT_SKU, PRODUCT_NAME, PRODUCT_PRICE),
				TestData.createProduct(PRODUCT_SKU, PRODUCT_NAME, PRODUCT_PRICE));
		when(productRepository.findAll()).thenReturn(productList);
		List<Product> products = productApi.listProducts();
		assertEquals(2, products.size());
		
	}
	
	@Test
	public void shouldUpdateProduct() {
		Product oldProduct = TestData.createProduct(PRODUCT_SKU, PRODUCT_NAME, PRODUCT_PRICE);
		Product newProduct = TestData.createProduct(PRODUCT_SKU, PRODUCT_NEW_NAME, PRODUCT_NEW_PRICE);
		when(productRepository.findById(PRODUCT_SKU)).thenReturn(Optional.of(oldProduct));
		when(productRepository.save(any(Product.class))).thenAnswer(p -> p.getArguments()[0]);
		Product updatedProduct = productApi.updateProduct(PRODUCT_SKU, newProduct);
		assertEquals(PRODUCT_NEW_NAME, updatedProduct.getName());
		assertEquals(BigDecimal.valueOf(PRODUCT_NEW_PRICE), updatedProduct.getPrice());
	}
	
	@Test
	public void shouldFailOnUpdatingNotExistingProduct() {
		when(productRepository.findById(PRODUCT_SKU)).thenReturn(Optional.empty());
		ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, 
				() -> productApi.updateProduct(PRODUCT_SKU, TestData.createProduct(PRODUCT_SKU, PRODUCT_NAME, PRODUCT_PRICE)));
	}
	
	@Test
	public void shouldDeleteProduct() {
		when(productRepository.existsById(PRODUCT_SKU)).thenReturn(Boolean.TRUE);
		productApi.deleteProduct(PRODUCT_SKU);
		verify(productRepository).deleteById(PRODUCT_SKU);
	}
	
	@Test
	public void shouldFailOnDeletingNotExistingProduct() {
		when(productRepository.existsById(PRODUCT_SKU)).thenReturn(Boolean.FALSE);
		ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, 
				() -> productApi.deleteProduct(PRODUCT_SKU));
	}
}
