package org.springframework.boot.demoproject.domain.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.demoproject.domain.product.exception.ProductAlreadyExistsException;
import org.springframework.boot.demoproject.domain.product.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        boolean productAlreadyExists = productRepository.existsById(product.getSku());
        if (!productAlreadyExists) {
            return productRepository.save(product);
        }
        throw new ProductAlreadyExistsException(String.format("Product with SKU %s already exists.", product.getSku()));
    }

    public Product updateProduct(Product product) {
        Product storedProduct = productRepository.findById(product.getSku()).orElseThrow(() ->
                new ProductNotFoundException(String.format("Product with SKU %s not found", product.getSku()))
        );
        storedProduct.setName(product.getName());
        storedProduct.setPrice(product.getPrice());
        productRepository.save(storedProduct);
        return storedProduct;
    }

    public void deleteProduct(String productSku) {
        boolean productExists = productRepository.existsById(productSku);
        if (productExists) {
            productRepository.deleteById(productSku);
        } else {
            throw new ProductNotFoundException(String.format("Product with SKU %s not found", productSku));
        }
    }
}
