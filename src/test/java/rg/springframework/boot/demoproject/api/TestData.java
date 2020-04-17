package rg.springframework.boot.demoproject.api;

import java.math.BigDecimal;

import org.springframework.boot.demoproject.model.Product;

public class TestData {

	public static Product createProduct(String sku, String name, Double price) {
		Product product = new Product();
		product.setSku(sku);
		product.setName(name);
		product.setPrice(BigDecimal.valueOf(price));
		return product;
	}
}
