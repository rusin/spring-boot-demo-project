package org.springframework.boot.demoproject.domain.order;

public class UnknownOrderedProductException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnknownOrderedProductException(String message) {
        super(message);
    }
}
