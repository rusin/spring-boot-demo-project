package org.springframework.boot.demoproject.api;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.demoproject.exception.ProductAlreadyExistsException;
import org.springframework.boot.demoproject.exception.ProductNotFoundException;
import org.springframework.boot.demoproject.model.Product;
import org.springframework.boot.demoproject.peristence.ProductRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@Api(value = "Product API")
public class ProductApi {

	private static Logger LOG = LoggerFactory.getLogger(ProductApi.class);
	
	@Autowired
	private ProductRepository productRepository;
	
	@PostMapping
	@ApiOperation(value = "Creates new product", response = Product.class)
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "New product successfully created"),
	    @ApiResponse(code = 400, message = "The product already exists")
	})
	public Product createProduct(@RequestBody @Valid Product product) {
		boolean productAlreadyExists = productRepository.existsById(product.getSku());
		if (!productAlreadyExists) {
			return productRepository.save(product);
		}
		throw new ProductAlreadyExistsException(String.format("Product with SKU %s already exists.", product.getSku()));
	}
	
	@GetMapping
	@ApiOperation(value = "Returns product list", responseContainer="List", response = Product.class)
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "Returns list of products")
	})
	public List<Product> listProducts() {
		return productRepository.findAll();
	}
	
	@PutMapping("/{sku}")
	@ApiOperation(value = "Updates existing product", response = Product.class)
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "The product successfully updated"),
	    @ApiResponse(code = 404, message = "The product you trying to update not found")
	})
	public Product updateProduct(@ApiParam(value = "The stock keeping unit", required = true)
			@PathVariable(name = "sku") String productSku, @RequestBody Product product) {
		LOG.debug("Update product with SKU {}", productSku);
		Optional<Product> productOptional = productRepository.findById(productSku);
		if (productOptional.isPresent()) {
			Product storedProduct = productOptional.get();
			storedProduct.setName(product.getName());
			storedProduct.setPrice(product.getPrice());
			return productRepository.save(storedProduct);
		} else {
			throw new ProductNotFoundException(String.format("Product with SKU %s not found.", productSku));
		}
	}
	
	@DeleteMapping("/{sku}")
	@ApiOperation(value = "Deletes product", response = Product.class)
	@ApiResponses(value = {
	    @ApiResponse(code = 200, message = "The product successfully deleted"),
	    @ApiResponse(code = 404, message = "The product you were trying to delete is not found")
	})
	public void deleteProduct(@ApiParam(value = "The stock keeping unit", required = true) @PathVariable(name = "sku") String productSku) {
		LOG.debug("Delete product with sku {}", productSku);
		boolean productxists = productRepository.existsById(productSku);
		if (productxists) {
			productRepository.deleteById(productSku);
		} else {
			throw new ProductNotFoundException(String.format("Product with SKU %s not found.", productSku));
		}
	}
}
