package org.springframework.boot.demoproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Product not found.")
public class ProductNotFoundException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;

	public ProductNotFoundException(String message) {
        super(message);
    }
}
