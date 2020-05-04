package org.springframework.boot.demoproject.application;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.demoproject.application.model.product.ProductCreateRequest;
import org.springframework.boot.demoproject.application.model.product.ProductMapper;
import org.springframework.boot.demoproject.application.model.product.ProductResponse;
import org.springframework.boot.demoproject.application.model.product.ProductUpdateRequest;
import org.springframework.boot.demoproject.domain.product.Product;
import org.springframework.boot.demoproject.domain.product.ProductService;
import org.springframework.boot.demoproject.domain.product.exception.ProductAlreadyExistsException;
import org.springframework.boot.demoproject.domain.product.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @GetMapping
    public List<ProductResponse> listProducts() {
        return productService.getProducts().stream()
                .map( pr -> productMapper.mapToProductResponse(pr))
                .collect(Collectors.toList());
    }

    @PostMapping
    public ProductResponse createProduct(@RequestBody @Valid ProductCreateRequest productCreateRequest) {
        try {
            Product product = productService.createProduct(productMapper.mapToProduct(productCreateRequest));
            return productMapper.mapToProductResponse(product);
        } catch (ProductAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PutMapping("/{sku}")
    public ProductResponse updateProduct(@PathVariable("sku") String productSku, @RequestBody @Valid ProductUpdateRequest productUpdateRequest) {
        try {
            Product product = productService.updateProduct(productMapper.mapToProduct(productSku, productUpdateRequest));
            return productMapper.mapToProductResponse(product);
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @DeleteMapping("/{sku}")
    public void removeProduct(@PathVariable("sku") String productSku) {
        try {
            productService.deleteProduct(productSku);
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
