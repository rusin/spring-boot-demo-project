package org.springframework.boot.demoproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Product already exists.")
public class ProductAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
